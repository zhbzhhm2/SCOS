# SCOS
E 2
Group K
主题: Use Intent and Intent Filter
内容:
1. 在 E 1 的 SCOS 工程包 es.source.code.activity 下定义一个新 Activity 类
MainScreen.java
2. 在MainScreen的屏幕布局中设计SCOS系统的主功能导航，导航项含：
点菜，查看订单，登录/注册，系统帮助。要求导航项有图标和文字，
并且可点击。
3. 在 AndroidManifest.xml 中添加 MainScreen 并定义其 IntentFilter 属性，添加
一 个 Action 值 为 “ scos.intent.action.SCOSMAIN ” 和 一 个 Category 值 为
“scos.intent.category.SCOSLAUNCHER”
4. 修改 E 1 中 SCOSEntry.java 类的代码，实现当用户在 SCOSEntry 屏幕上水平
向左滑动时，从 SCOSEntry 屏幕跳转到 MainScreen 屏幕，并使用 Intent 向
MainScreen 类传递一个 String 数据值“FromEntry”
5. 修改 MainScreen.java 类的代码，当屏幕跳转至 MainScreen 时，获取 SCOSEntry
通过 Intent 传递过来的 String 值，并作判断是否和“FromEntry”相等；如果相
等，则正常显示当前屏幕；如果不相等，则隐藏导航项：点菜，查看订单。测
试该功能是否正确运行？如不能，请根据调试信息，修改代码使之能正确运行
6. 在 SCOS 工 程 AndroidManifest.xml 中定义 Permission 值 为
“scos.permission.ACCESSSCOS”，并将该 Permission 属性 protection
level 设置为“dangerous”
7. 修改 AndroidManifest.xml 中 MainScreen 的属性 android:permission，并将值
设为“scos.permission.ACCESSSCOS”。再次测试从 SCOSEntry 屏幕跳转到
MainScreen 屏幕，观察是否能正确运行？如不能，请根据调试信息，修改代码
使之能正确运行。
8. 在 SCOS 工程包 es.source.code.activity 下 定 义 一 个 新 Activity 类
LoginOrRegister.java
9. 在 LoginOrRegister 布局中添加“登录名”输入框和“登录密码”输入框，将“登
录密码”输入框类型设置为 Password，添加按钮“登录”和“返回”
10. 当用户在 LoginOrRegister 屏幕中，点击登录按钮时，验证“登录名”和“登
录密码”是否符合以下规则：不为空，只包含英文大小写和数字。当输入内容
不符合规则时，则使用 setError 方法在当前输入框出提示错误信息“输入内容
不符合规则”。当符合规则后，屏幕跳转至 MainScreen，并向 MainScreen 类传
递一个数据 String 值为“LoginSuccess”
11. 当用户在 LoginOrRegister 屏幕中，点击返回按钮时，屏幕跳转至 MainScreen，
并向 MainScreen 类传递一个数据 String 值为“Return”
12. 修改 MainScreen.java 代码，判断由 LoginOrRegister 传回的 String 数据值。如果
返回数据为“LoginSuccess”，则检查“点菜”和“查看订单”是否为隐藏状态，
如果为隐藏，则设为显示
中国科学技术大学 软件学院（苏州）
信息来自 http://staff.ustc.edu.cn/~waterzhj/ 第 2 页 共 2 页
13. 新建一个工程为 TestSCOS，在该工程中添加 Activity 类 TestMain.java，在
TestMain 屏幕中添加一个按钮“SCOS”，当点击此按钮时，屏幕跳转至 SCOS
的 MainScreen 屏幕。请根据调试信息，解决程序错误，达到正确运行此功能。
14. 请查阅资料总结 android:protectionlevel 的不同类型，并说明如何使用。
15. 请查阅资料总结 IntentFilter 如何测试 Action、Category、Data，并说明 IntentFilter
用法。
