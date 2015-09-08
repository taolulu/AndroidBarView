# AndroidBarView
My project display percentages with this simple bar view.  
一个用来显示百分比的简单柱形控件。
#Feature
Update/reset value with animation.
更新或设置值的时候会有平滑的过渡效果。
#Usage
sample with default value
```xml
<com.practice.barView.BarView
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:id="@+id/bar_view"
        app:cornerRadius="0"
        app:innerCorner="false"
        app:barColor="@android:color/holo_orange_dark"
        app:backgroundColor="@android:color/darker_gray"
        app:animDuration="500"
        app:percent="0"/>
```
