#Android Studio 性能检测工具  

* <Font size="5">TraceView</font>  
	查看某段操作时间内，程序执行的全部方法及执行时间、执行次数。可调试卡顿问题。
*  <Font size="5">Link</font>  
	静态测试工具，扫描和检查代码中可能存在的问题。不是检查异常或者BUG，而是优化代码。
*  <Font size="5">Android Monitor(Memory项)</font>  
	当前操作带来的内存的使用及变化情况。如果短时间内变化很大，说明存在内存抖动问题。  
*  <Font size="5">Heap</font>  
	内存快照，查看对象的内存使用情况。通过内存回收操作前后的对比，检测内存泄露问题。  
	也可以将内存使用情况Dump成hprof格式文件，使用<Font color="red">MAT</font>工具进行详细分析。
*  <Font size="5">Allocation Tracker</font>  
	追踪一段时间内存的分配情况，详细列出每个数据类型内存分配的栈信息。 
*  <Font size="5">Networking Traffic Tool</font>  
	查看网络请求的时间，每次请求的数据量等信息。


---------------------------------------  
#其他工具  
  
*  LeakCanary：检测内存泄露的工具，代码量非常庞大或者偶尔出现内存问题的情况下非常好用。
*  ProGuard：代码混淆工具，还能够优化代码，去除冗余代码。
*  LitePal：Sqlite数据库的开源项目。
*  JobScheduler：google推出的性能优化工具(参见Android性能优化典范)。


---------------------------------------
#Android 单元测试工具  

* <Font size = "5">Robolectric</font>  
  实现了一套JVM能运行的Android代码，可以在JVM上运行android相关的代码，无需android运行环境。  
  [Android单元测试框架Robolectric3.0介绍(一)](http://www.jianshu.com/p/9d988a2f8ff7)	
* <Font size = "5">Mock框架(Mockito)</font>   
  适用于测试对象依赖度较高而需要解除依赖的场景。  
* <Font size = "5">Monkey</font>     
  Android提供的系统工具。它向系统发送伪随机的用户事件流，进行压力测试。  
  [Android Monkey测试介绍](http://www.jikexueyuan.com/course/1609_1.html)


--------------------------------
#Android多媒体  

* <Font size = "5">Android SDK</font>  
  MediaPlayer + SurfaceView或VideoView，开发简单快速，支持的格式比较单一。	 
* <Font size = "5">Exoplayer</font>  
  google的开源项目，支持SDK16以上的版本，具有更好的扩展性及定制，支持的格式比较少。	
* <Font size = "5">百度媒体云</font>  
  内嵌百度自主研发的T5播放内核，对目前主流的本地和网络媒体都有良好的支持。
* <Font size = "5">Vitamio</font>   
  第三方强大完善的播放框架，使用ffmpeg技术进行解析和解码，支持格式多样，功能全面。
* <Font size = "5">VLC</font>  
  支持众多音频与视频解码器及文件格式，融合了FFmpeg计划的解码器与libdvdcss程序库，android开发可学习vlc for android。
* <Font size = "5">ffmpeg</font>  
  可以用来记录、转换数字音频、视频，并能将其转化为流的开源框架，支持平台广泛，是各类开源播放器的基础，难度较高，andorid需要用jni编译。   


---------------------------------------
# 技术网站

* [SegmentFault](https://segmentfault.com)
* [推酷](http://www.tuicool.com)
* [伯乐在线](http://blog.jobbole.com)
* [简书](http://www.jianshu.com)
* [开源中国](http://www.oschina.net)
* [CSDN](http://www.csdn.net)
* [Android开发工具](http://www.androiddevtools.cn)
* [慕课网](http://www.imooc.com)



----------------------------------------
* [git使用教程](http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)
* [git管理Android Studio项目教程](http://www.wfuyu.com/technology/22499.html)


----------------------------------------
* [程序员必读书单](http://lucida.me/blog/developer-reading-list/)  
	
		 

  
  
  


