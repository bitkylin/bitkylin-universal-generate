# bitkylin-universal-generate

![Build](https://github.com/bitkylin/bitkylin-universal-generate/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

## Template ToDo list
- [x] Create a new [IntelliJ Platform Plugin Template][template] project.
- [ ] Get familiar with the [template documentation][template].
- [ ] Adjust the [pluginGroup](./gradle.properties), [plugin ID](./src/main/resources/META-INF/plugin.xml) and [sources package](./src/main/kotlin).
- [ ] Adjust the plugin description in `README` (see [Tips][docs:plugin-description])
- [ ] Review the [Legal Agreements](https://plugins.jetbrains.com/docs/marketplace/legal-agreements.html?from=IJPluginTemplate).
- [ ] [Publish a plugin manually](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate) for the first time.
- [ ] Set the `PLUGIN_ID` in the above README badges.
- [ ] Set the [Plugin Signing](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html?from=IJPluginTemplate) related [secrets](https://github.com/JetBrains/intellij-platform-plugin-template#environment-variables).
- [ ] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html?from=IJPluginTemplate).
- [ ] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.

<!-- Plugin description -->

### What's this?

- Acting on Controller classes or POJO classes, you can easily generate Protostuff annotations (@Tag) with a single click and directly convert JavaDoc to Swagger annotations (@Api, @ApiOperation, @ApiModel, @ApiModelProperty) with a single click.

### 有什么用呢？

- 作用于Controller类或POJO类，你可以很方便的一键生成Protostuff注解(@Tag)，并且可以一键直接将JavaDoc转换为Swagger注解(@Api, @ApiOperation, @ApiModel, @ApiModelProperty)。反过来，你也可以一键将Swagger注解转换为JavaDoc。

### Features

1. You can generate Swagger annotations (@Api, @ApiOperation, @ApiModel, @ApiModelProperty) with one click, and Protostuff annotations (@Tag) with one click via the right-click menu.
2. you can through the right-click menu , one-click Swagger annotations converted to JavaDoc annotations .
3. Currently supports two languages, English and Chinese, you can switch through the "settings -> tools -> Bitkylin Universal Generate" page.
4. If you only need to manipulate Swagger annotations but not Protostuff annotations , and vice versa, don't worry, you can switch through the "settings -> tools -> Bitkylin Universal Generate" page.

### 特性

1. 你可以通过右键菜单，一键生成Swagger注解(@Api, @ApiOperation, @ApiModel, @ApiModelProperty), 一键生成Protostuff注解(@Tag)。
2. 你可以通过右键菜单，一键将Swagger注解转换为JavaDoc注释。
3. 当前支持两种语言，英文和中文，可以通过 "settings -> tools -> Bitkylin Universal Generate" 页面进行切换。
4. 如果你只需要操作Swagger注解而不需要操作Protostuff注解，反之亦然，不用担心，你可以通过 "settings -> tools -> Bitkylin Universal Generate" 页面进行切换。

### Tip.

#### NO.1

If you want to generate @Tag annotations for the entire document according to a customized starting number, just set the starting number in the first field in the class, then execute "Generate Entry Annotation" ,for example:

如果你想按照自定义的起始序号，生成整个文档的@Tag注解，只需要在类中的第一个字段设置起始序号即可，然后执行"生成入口注解" ,例如：

```java
@Data
public class TestRequest {

    @Tag(105)
    private String name;

    /**
     * bitkylin age
     */
    private Integer age;

    private Address address;

    public static class Address {

        @Tag(208)
        private String province;

        private String city;

        private String detailedAddress;
    }
}
```

<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "bitkylin-universal-generate"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/bitkylin/bitkylin-universal-generate/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation