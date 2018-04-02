# FreeLine

阿里Gradle加速使用指南。

## FreeLine介绍

Freeline是由阿里巴巴蚂蚁聚宝Android团队开发的一款针对Android平台的增量编译工具，它可以充分利用缓存文件，在几秒钟内迅速地对代码的改动进行编译并部署到设备上，有效地减少了日常开发中的大量重新编译与安装的耗时。

[Freeline代码开源在Github](https://github.com/alibaba/freeline)

[Freeline在云栖社区的原理说明](https://yq.aliyun.com/articles/59122?spm=5176.8091938.0.0.1Bw3mU)

## 特性

- 支持标准的多模块 Gradle 工程的增量构建
- 并发执行增量编译任务
- 进程级别异常隔离机制
- 支持 so 动态更新
- 支持 resource.arsc 缓存
- 支持 retrolambda
- 支持 DataBinding
- 支持各类主流注解库
- 支持 Windows，Linux，Mac 平台

## 局限性

- 第一次增量资源编译的时候可能会有点慢
- 不支持删除带 id 的资源，否则可能导致 aapt 编译出错
- 暂不支持抽象类的增量编译
- 不支持开启 Jack 编译
- 不支持 Kotlin/Groovy/Scala

## 如何安装FreeLine

### FreeLine安装前的准备

1.安装python、并配置系统环境变量

因为Freeline是用python写的，下面的的所有操作，包括编译等都要依赖python，所以我们要安装python，并且配置系统环境变量。

Python分2.x版本和3.x版本，但是Freeline目前只支持2.x版本的，以前学过一点点python，这里为大家推荐2.7.12版：[点击下载](https://www.python.org/downloads/release/python-2712/)

### FreeLine的项目初始化安装

1.配置项目根目录的 build.gradle，加入 freeline-gradle 的依赖：


```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.antfortune.freeline:gradle:0.8.8'
    }
}

```

2.然后，在你的主 module 的 build.gradle 中，应用 freeline 插件的依赖：

```
apply plugin: 'com.antfortune.freeline'

android {
    ...
}

```

3.在命令行执行以下命令来下载 freeline 的 python 和二进制依赖。

- Windows: gradlew initFreeline
- Linux/Mac: ./gradlew initFreeline

注意点：

- 如果你的下载的时候速度很慢，你也可以加上参数，执行gradlew initFreeline -Pmirror，这样就会从国内镜像地址来下载。

- 你也可以使用参数`-PfreelineVersion={your-specific-version}`来下载特定版本的 freeline 依赖。

- 如果你的工程结构较为复杂，在第一次使用 freeline 编译的时候报错了的话，你可以添加一些 freeline 提供的配置项，来适配你的工程。具体可以看 [Freeline DSL References](https://github.com/alibaba/freeline/wiki/Freeline-DSL-References)。


完成如上操作后，你的项目中会增加如下内容：

![](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/img/freeline/freeline_demo.png)

至此，你已经安装完毕所有的freeline依赖。


## 如何使用FreeLine

### 运行项目

第一次运行是需要全量编译，所以第一次的时间相对较长，甚至比gradle编译还要长，但是后期的运行会很快。
第一次全量编译，在命令行输入：

```
python freeline.py -f
```

如果我们后期修改了代码，秒秒钟就可以热更新到手机

```
python freeline.py
```

如果你要调试应用，在命令行输入：

```
python freeline.py -d
```

### 常用命令

执行命令 | 描述
-----|------
python freeline.py | 增量编译
python freeline.py -f | 全量编译 cleanBuild 强制执行一次 clean build
python freeline.py -d | 调试 打开debug模式
python freeline.py -h | 帮助 显示帮助信息并退出
python freeline.py -v | 版本 显示版本信息
python freeline.py -w | 等待 让应用程序等待 debugger
python freeline.py -a | 全部 在所有工程上强制执行clean build 并执行-f全量编译
python freeline.py -c | 清空 清空缓存目录和工作空间
python freeline.py -i | 初始化 对工程进行进行freeline初始化配置


## 速度测试

为了测试freeline加速的效果，我使用[本项目](https://github.com/xuexiangjys/GoogleComponentsDemo)进行测试

测试内容：

- 第一次全量编译
- 第二次修改项目代码(修改内容如下图)，进行增量编译。

![](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/img/freeline/change.png)

### Instant Run

1.第一次进行全量编译：

![](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/img/freeline/instant_first.png)

2.第二次进行增量编译：

![](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/img/freeline/instant_second.png)


### FreeLine

1.第一次进行全量编译：

![](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/img/freeline/freeline_first.png)

2.第二次进行增量编译：

![](https://github.com/xuexiangjys/GoogleComponentsDemo/blob/master/img/freeline/freeline_second.png)

由上可以看到，freeline第一次进行全量编译所需要的时间要远大于Instant Run，但是在后面修改代码后进行增量编译时，freeline明显要快好多。

## 常见问题

1.问：使用python freeline.py -d无法进行debug断点调试？
答：“python freeline.py -d" 中的-d 不是我想象中的进行debug，而是输出freeline的log参数；如果想进行debug，要结合android studio的attach debugger 按钮。

2.问：资源文件(res下)修改后，编译闪退问题。
答：闪退现象是：单单资源文件修改会闪退，资源文件+java文件修改正常。
github上的回复是：这个现象不是闪退，而是我的activity在重建的时候调用了两次finish，把重建的activity的关掉了。
暂时解决方案是：如果单单修改资源文件，那么找个java文件 打个空格保存一下，这样也不是特别麻烦，可以接受，先这样用着吧。

其实，在修改个别资源文件，进行增量编译时，可能会导致程序崩溃。因此，我建议如果你修改了资源文件，还是不要使用freeline的增量编译。

[其他常见问题点击查看](https://github.com/alibaba/freeline/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)
