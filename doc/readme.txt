server.xml  

<Context path="" docBase="cims" debug="false" reloadable="true" />


在catalina.bat的第一行增加：  
  
set JAVA_OPTS=-Xms1024m -Xmx1024m -Xss1024K -XX:PermSize=64m -XX:MaxPermSize=128m    
  
  
参数详解  
  
-Xms  JVM初始化堆内存大小  
-Xmx  JVM堆的最大内存  
-Xss   线程栈大小   
-XX:PermSize JVM非堆区初始内存分配大小  
-XX:MaxPermSize JVM非堆区最大内存  
  

<Connector port="8080"    
           protocol="org.apache.coyote.http11.Http11AprProtocol"   
           connectionTimeout="20000" //链接超时时长   
           redirectPort="8443"    
           maxThreads="500"//设定处理客户请求的线程的最大数目，决定了服务器可以同时响应客户请求的数,默认200   
           minSpareThreads="20"//初始化线程数，最小空闲线程数，默认为10  
           acceptCount="1000" //当所有可以使用的处理请求的线程数都被使用时，可以被放到处理队列中请求数，请求数超过这个数的请求将不予处理，默认100  
           enableLookups="false"    
           URIEncoding="UTF-8" />  