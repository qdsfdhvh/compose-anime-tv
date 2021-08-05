# Compose-Anime-TV

## 知识梳理

### 1.主题配置

```kotlin
fun lightColors(
    primary: Color = Color(0xFF6200EE),
    primaryVariant: Color = Color(0xFF3700B3),
    secondary: Color = Color(0xFF03DAC6),
    secondaryVariant: Color = Color(0xFF018786),
    background: Color = Color.White,
    surface: Color = Color.White,
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.White
) {}
```

![material-theme](https://gitee.com/seduce/img/raw/master/markdown/unnamed.png)

### 2.了解Effect（副作用）

Compose是通过反复执行函数来刷新UI的，而在开发中必然有一些需要在`onCreate`、`onDestroy`或者其他时机运行的代码，他们不能跟随UI刷新时重复运行，这时候就需要使用`Effect`；

这方面我参考了[Jetpack Compose Side Effect：如何处理副作用 @fundroid](https://juejin.cn/post/6930785944580653070)和[官方文档](https://developer.android.google.cn/jetpack/compose/side-effects)，其中特别推荐fundroid的这篇文章，写得非常好，连（副作用）的命名都有解释。

## 参考

### 文章

- [官方compose文档 @Google](https://developer.android.google.cn/jetpack/compose)
- [Jetpack Compose在Twidere X中的实践总结 @Tlaster](https://zhuanlan.zhihu.com/p/351794462)
- [Jetpack Compose 中显示富文本 @Tlaster](https://zhuanlan.zhihu.com/p/369109654)
- [Focus in Jetpack Compose @Jamie Sanson](https://medium.com/google-developer-experts/focus-in-jetpack-compose-6584252257fe)

### 项目

- [TwidereX-Android @Tlaster](https://github.com/TwidereProject/TwidereX-Android)
- [SampleComposeApp](https://github.com/akilarajeshks/SampleComposeApp)
