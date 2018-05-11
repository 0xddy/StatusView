# StatusView
一个简单的状态展示，如加载中、网络错误等等
<<<<<<< HEAD


StatusView statusView = findViewById(R.id.status_view);

//可以注册自己的布局，不局限于加载中、网络错误等
View view = getLayoutInflater().inflate(R.layout.layout_err, null);
statusView.registerView(10086, view);

//重置、清空状态
statusView.removeStatus();
//有改变就需要调用
statusView.notifyDataSetChanged();

//setStatus 可以给resId 也可以给 自己 registerView 注册自定义布局时的 key ,这里R.layout.layout_loading是框架自带的示例布局,需要在定义xml时添加上自定义属性
statusView.setStatus(R.layout.layout_loading);
statusView.notifyDataSetChanged();


xml 

    <com.tangdunguanjia.statusview.StatusView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
		
		.........
		
    </com.tangdunguanjia.statusview.StatusView>

自定义属性 

		<attr name="loading_view" format="reference" />
        <attr name="err_view" format="reference" />
        <attr name="empty_view" format="reference" />
 
