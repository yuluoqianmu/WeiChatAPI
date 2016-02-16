package com.lyz.validate;

/**
 * All the validated object must implements this interface
 * @author jiangtch
 *框架说明：

AnnotationValidable接口：所有需要使用该校验框架的类都要实现它，该类中没有任何方法需要实现，仅仅是一个表明那些类需要使用该校验框架的标识。

GetFiledValue类：是一个工具类，对外提供一个静态方法public static Object getField(AnnotationValidable filter, String field)用于获得指定类对象的指定属性值。

Handler接口：是所有校验处理的接口。

ValidateException类：是校验框架中任何异常均被封装成该异常对象向上抛出。

 

该校验框架所提供的校验注解有：

@ValidateDigit:校验是否是数字。

@ValidateInt:校验整型数据，可以指定最大值或最小值。

@ValidateNotEmpty:校验集合对象中不能为空。

@ValidateNotLaterThan:校验一个时间或日期不能比另一个时间或日期晚。

@ValidateNotNull:校验对象不能为NULL。

@ValidatePattern:针对字符串的高级校验，可以指定字符串的正则表达式。

@ValidateSize:校验字符串的长度，可以指定最长长度或最短长度。

@ValidateStringIn:校验字符串中包含指定的字符（串），多个字符（串）用“，”分隔。

所有以上的注解都有一个message属性用于指定校验出差时异常信息内容，都有默认值，可以指定也可以不用指定。

这些注解里面的一些其他参数用法请参考UT中的测试用例。

 

以上所有的注解都有一个对应的Handler用于处理该注解，这些handler都实现Handler接口。

这些Handler具体负责每个对应的注解的校验，如果校验失败则抛出ValidateException异常。

 

校验框架最核心的是Validator，这个类是一个单态模式，使用时只需要调用其public void validate(AnnotationValidable validatedObj)方法，把需要校验的实现了AnnotationValidable接口的对象当作参数传进去就可以了。

Validator的工作原理是：

1.获得校验对象后，扫描该对象中是否存在自定义的校验注解，存在就去调用相应的注解处理类（对应的handler）进行校验。

2.校验对象校验结束后，递归校验其父类，直到AnnotationValidable接口为止。

 

优点：

1.使用方便，重用性高。

2.可以根据需求进行定制化开发。

3.适合后台的数据校验。

 

不足：

1.对应java Annotation不熟悉的人可能不太好上手。

2.有重复造轮子之嫌。

3.功能有限，目前只实现了在字段上注解的校验，尚未实现对get方法和类作用域注解的支持。
 */
public interface AnnotationValidable {

}
