package com.jiangli.amazon.lambda.wx.gzh.helper;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/7/4 16:45
 */
@Data
public class XmlToBeanParser<T> {
    private List<T> list;
    
    public  static <T> XmlToBeanParser<T> parseByFile(String xmlFilePath,String tagName,  Class<T> cls){
        try {
            return parse(new FileInputStream(xmlFilePath),tagName,cls);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static <T> XmlToBeanParser<T> parseByContent(String content,String rootTagName, Class<T> cls){
        try {
            return parse(new ByteArrayInputStream(content.getBytes()),rootTagName,cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public  static <T> XmlToBeanParser parse(InputStream contentStream, String rootTagName, Class<T> cls){
        XmlToBeanParser<T> ret = new XmlToBeanParser<T>();
        List<T> dt = new LinkedList<T>();
        ret.setList(dt);



        DocumentBuilderFactory builderFactory =  DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();

            Document doc = builder.parse(contentStream);
            Element root = doc.getDocumentElement();

            Node find = findNodeRecursive(root,rootTagName);

            if (find != null) {
                Element findRoot = (Element) find;

                T t = parseElementToBean(findRoot,cls);
                dt.add(t);

                //BeanUtils.populate(t,map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    private static <T> T parseElementToBean(Element findRoot, Class<T> cls) {
        T t = null;
        try {
            t = cls.newInstance();

            NodeList childNodes = findRoot.getChildNodes();

            //每一个子节点属性
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);

                String nodeName = item.getNodeName();
                try {
                    PropertyDescriptor descriptor = new PropertyDescriptor(nodeName, cls);
                    Method readMethod = descriptor.getReadMethod();
                    Class<?> returnType = readMethod.getReturnType();
                    if (item.getChildNodes().getLength() > 1) {
                        //    对象属性
                        descriptor.getWriteMethod().invoke(t,parseElementToBean((Element)item,returnType));
                    } else {
                        String textContent = item.getTextContent();
                        Object val = textContent;
                        //如果对象属性是数字
                        if (Number.class.isAssignableFrom(returnType)) {
                            try {
                                long v = Long.parseLong(textContent);
                                val = new Long(v);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                        }

                        //普通属性
                        descriptor.getWriteMethod().invoke(t, val);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }


                //map.put(nodeName,item.getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    private static Node findNodeRecursive(Node root, String rootTagName) {
        if(rootTagName.equals(root.getNodeName())) {
            return root;
        }

        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (rootTagName.equals(item.getNodeName())) {
                return item;
            }
        }

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            Node nodeRecursive = findNodeRecursive(item, rootTagName);
            if (nodeRecursive != null) {
                return nodeRecursive;
            }
        }


        return null;
    }

    private static String getPropName(String attrName) {
        if("interface".equals(attrName)){
            return "interfaceName";
        }
        return attrName;
    }


    public   T get() {
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
    public  List<T> list() {
        return list;
    }
}
