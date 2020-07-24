package test;





import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BusParse {

    // tag값의 정보를 가져오는 메소드
 private static String getTagValue(String tag, Element eElement) {
     NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
     Node nValue = (Node) nlList.item(0);
     if(nValue == null) 
         return null;
     return nValue.getNodeValue();
 }

 public static void main(String[] args) {
 
  try{
  
    // parsing할 url 지정(API 키 포함해서)
       StringBuilder urlBuilder = new StringBuilder("http://openapitraffic.daejeon.go.kr/api/rest/arrive/getArrInfoByStopID"); /*URL*/
       urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=VROsvJATDdBKZfYtHs8Xt597RoykwjQYm9Y%2BB7HdiESeAJkhlvSu7kmiLHt8ZrdLOWKdA%2FRq1C23hJeyQChPNQ%3D%3D"); /*Service Key*/
       urlBuilder.append("&" + URLEncoder.encode("BusStopID","UTF-8") + "=" + URLEncoder.encode("8001378", "UTF-8")); /*정류소ID(7자리)*/
       String url = new String(urlBuilder.toString());
    
    DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
    Document doc = dBuilder.parse(url);
    
    // root tag 
    doc.getDocumentElement().normalize();
    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    
    // 파싱할 tag
    NodeList nList = doc.getElementsByTagName("itemList");
    //System.out.println("파싱할 리스트 수 : "+ nList.getLength());
    
    for(int temp = 0; temp < nList.getLength(); temp++){
     Node nNode = nList.item(temp);
     if(nNode.getNodeType() == Node.ELEMENT_NODE){
      
      Element eElement = (Element) nNode;
      System.out.println("######################");
      //System.out.println(eElement.getTextContent());
      System.out.println("사용자 위치 : " + getTagValue("STOP_NAME", eElement));
      System.out.println("버스번호 : " + getTagValue("ROUTE_NO", eElement));
      System.out.println("현재 위치  : " + getTagValue("DESTINATION", eElement));
      System.out.println("남은시간  : " + getTagValue("EXTIME_MIN", eElement)+"분");
     } // for end
    } // if end
    
  
   
  } catch (Exception e){ 
   e.printStackTrace();
  } // try~catch end
 } // main end
} // class end
