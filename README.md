# SoSoApp
 
框架描述：<br>
(1)遵循Jetpack MVVM 设计模<br>
ViewModel 用来托管view状态和事件，ViewModel 作为 视图控制器 和 数据层 沟通的桥梁。<br>
(2)数据驱动。使用DataBinding来bind页面view，LiveData来观察业务逻辑数据的变化，通知状态ViewModel 更新ui。观察State页面多状态，驱动显示加载中、关闭、重试、空页面。<br>
(3)基础类功能封装。注册绑定bind view ， 提供DataBindingConfig页面配置文件。<br>
提供 ViewModel 作用域工厂，为Activity/Fragment 提供创建不同作用域ViewModel。<br>
(4)引用Jetpack Navigation 页面导航功能，实现单Activity多Fragment导航逻辑。<br>
(5)网络层。提供便捷式api调用工具类，只需注册实现回调接口成功或者失败。<br>
