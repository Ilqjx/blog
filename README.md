### Ilqjx的个人博客
- - -
编程不仅是一门技术，更是一门艺术。
> 参考b站Yancy02发布的视频，特此感谢。
>
> 会陆续对项目进行技术升级以及功能的完善，有兴趣的朋友可以一起交流。

环境：
1. idea
2. maven3
3. mysql5.7
4. springboot2.2.4

技术栈：
1. springboot
2. spring data jpa
3. thymeleaf模板引擎
4. Semantic UI

插件：
1. [MarkDown编辑器](https://pandao.github.io/editor.md)
2. [中文网页重设与排版](https://github.com/sofish/typo.css)
3. [二维码生成插件](https://davidshimjs.github.io/qrcodejs)
4. [CSS3动画库](https://daneden.github.io/animate.css)
5. [语法高亮插件](https://github.com/PrismJS/prism)
6. [目录生成插件](https://tscanlin.github.io/tocbot)
7. [动画滚动插件](https://github.com/flesler/jquery.scrollTo)
8. [滚动监听插件](http://imakewebthings.com/waypoints)
9. [Markdown转HTML](https://github.com/atlass)

项目结构：

个人博客
- 前端展示
    - 首页
        - 博客分页列表
        - 展示Top标签
        - 展示Top分类
        - 最新博客推荐
        - 博客详情
    - 分类
        - 展示所有分类
        - 展示单个分类下博客列表
        - 博客详情
    - 标签
        - 展示所有标签
        - 展示单个标签下博客列表
        - 博客详情
    - 归档
        - 按年度时间线展示博客列表
        - 博客详情
- 后端管理
    - 管理员登录
    - 博客管理
        - 发布博客
        - 修改博客
        - 删除博客
        - 查询博客
    - 分类管理
        - 新增分类
        - 修改分类
        - 删除分类
        - 查询分类
    - 标签管理
        - 新增标签
        - 修改标签
        - 删除标签
        - 查询标签

日志更新：
#### 2020
1. 美化页面及部分功能的完善和修复bug
2. 实现管理员登录的前端JS加密和后端Java解密(AES+BASE64)
3. 后端改用shiro进行身份认证和md5加密
4. 将项目部署到阿里云 [传送门](https://ilqjx.cn/)

