# TypeScript 基础入门

## 类型

### Boolean
- 布尔基元类型对应于类似命名的JavaScript基元类型，并表示true或false的逻辑值。
例子
```例子
var b: boolean;         // 显式输入
var yes = true;         // 隐式输入 
var no = false;         // 隐式输入
```

### Number
- TypeScript中的所有数字都是浮点值

例子
```例子
let decimal : number  =  6 ;
var x: number;          
var y = 0;              
var z = 123.456;        
var s = z.toFixed(2);   
```

### String
例子
```例子
var s: string;
var empty = "";         
var abc = 'abc';        
var c = abc.charAt(2);  
let fullName: string = `Bob Bobbington`;
let age: number = 37;
let sentence: string = `Hello, my name is ${ fullName }.
I'll be ${ age + 1 } years old next month.`;
```

### Array
例子
```例子
let list: number[] = [1, 2, 3];
let list: Array<number> = [1, 2, 3];
```

### Tuple
- 已知元素数量和类型的数组，各元素的类型不必相同

例子
```例子
let x: [string, number];
x = ["hello", 10];
x[0].substr(1)
```

### Enum
例子
```例子
enum Color {Red, Green, Blue}
let c: Color = Color.Green;
//or
enum Color {Red = 1, Green = 2, Blue = 4}
let c: Color = Color.Green;

//可以从数值转到枚举中值的名称
let colorName: string = Color[2];//colorName console 'Green'
```

### Any
例子
```例子
let notSure: any = 4;
notSure = "maybe a string instead";
notSure = false;

let list: any[] = [1, true, "free"];
list[1] = 100;
```

### Void
- void 没有任何类型,可以将此视为不返回值的函数的返回类型.声明类型的变量void是没有用的。
例子
```例子
function warnUser(): void {
     console.log（"这是我的警告信息"）; 
}
```

### Null and Undefined
- undefined和null实际是有分别自己类型的命名undefined和null，他们两个并不是很有用。null，undefined是所有其他类型的子类型.但是基本不会给对象属性声明为undefined or null

### Never
- 不会有返回值。通常用于函数表达式或箭头函数表达式的返回类型抛出异常或永不返回的异常。

### Object
- object表示非原始类型，也就是除number，string，boolean，symbol，null或undefined之外的类型。不可以直接给类型为object的对象赋值原始类型。
例子
```例子
declare function create(o: object | null): void;

create({ prop: 0 }); // OK
create(null); // OK
create(42); // Error
```

### Union
- Union 表示对象可能具有一个或多个不同类型
例子
```例子
var x: string | number;  
x = "hello";            // Ok  
x = 42;                 // Ok  
```

##  类型定义规范
- 尽可能的使用原始类型为对象定义类型 或定义 新建 interface 作为对象的类型
- 不使用any，以便增强代码的阅读性和方便后期的维护


## 变量声明
- var 声明
- let 声明
  - 块级作用域
```
function f(input: boolean) {
    let a = 100;

    if (input) {
        let b = a + 1;
        return b;
    }
    return b;//错误
}
```
  - 先定义后使用
  - 重定义及屏蔽，即使定义多个仍然
```
let x = 10;
let x = 20; // 错误，不能在1个作用域里多次声明`x`
```
- const 声明
  - 值不可变
```
  const numLivesForCat = 9;
  numLivesForCat=10；//错误
```

补充：readonly vs const
- 判断该用readonly还是const的方法是看要把它做为变量使用还是做为一个属性。 做为变量使用的话用 const，若做为属性则使用readonly。

##  变量声明规范
- var声明有些奇怪的作用域规则,请使用let or const 来代替var


## 接口
在TypeScript里，interface 是比较常用的。作用就是为这些类型命名和为你的代码或第三方代码定义契约

例子
```例子
let myObj = {size: 10, label: "Size 10 Object"};
printLabel(myObj);

interface LabelledValue {
  label: string;
}

function printLabel(labelledObj: LabelledValue) {
  console.log(labelledObj.label);
}

```

- 可选属性
```
interface LabelledValue {
  color?: string;//可选属性
}
```

- 只读属性
```
interface LabelledValue {
  readonly x: string;//赋值后不可改变。
}
```

- 可索引的类型
```
interface LabelledValue {
  [index: number]: string; //可索引的类型  
  //readonly [index: number]: string; 将索引签名设置为只读
  length: number;//这是错误的length的类型与索引类型返回值的类型不匹配
}

let myArray: LabelledValue;
myArray = ["Bob", "Fred","1212","true"];
let myStr: string = myArray[0];// "Bob"
```

- 函数类型
```
interface SearchFunc {
  (source: string, subString: string): boolean;
}

let mySearch: SearchFunc;
mySearch = function(src: string, sub: string): boolean {
  let result = src.search(sub);
  return result > -1;
}
```

- 类类型，实现接口
```
interface ClockInterface {
    currentTime: Date;
}

class Clock implements ClockInterface {
    currentTime: Date;
    constructor(h: number, m: number) { }
}
```

- 继承接口
```
interface Shape {
    color: string;
}

interface Square extends Shape {
    sideLength: number;
}

let square = <Square>{};
square.color = "blue";
square.sideLength = 10;
```

- 混合类型
```
interface Counter {
    (start: number): string;
    interval: number;
    reset(): void;
}

function getCounter(): Counter {
    let counter = <Counter>function (start: number) { };
    counter.interval = 123;
    counter.reset = function () { };
    return counter;
}
```

- 接口继承类
```
class Control {
    private state: any;
}

interface SelectableControl extends Control {
    select(): void;
}

class Image implements SelectableControl {
    private state: any;
    select() { }
}
```

##  接口规范
- 不要使用I做为接口名前缀。

##  类
例子
```
class Greeter {
    greeting: string;
    constructor(message: string) {
        this.greeting = message;
    }
    greet() {
        return "Hello, " + this.greeting;
    }
}

let greeter = new Greeter("world");
```
与C#或Java非常相似，不多做备注。
- 继承
- public,private,protected,readonly修饰符
- get set
- 静态属性
- 抽象类

##  函数
例
```
myAdd(x: number, y: number):number{
   return x + y; 
}

let myAdd: (x: number, y: number) => number =function(x: number, y: number): number { return x + y; };
```

- 可选参数
```
buildName(firstName: string, lastName?: string) {
    if (lastName)
        return firstName + " " + lastName;
    else
        return firstName;
}
```
- 默认初始化的参数
```
buildName(firstName: string, lastName = "Smith") {
    return firstName + " " + lastName;
}
```

- 剩余参数
  -必要参数，默认参数和可选参数有个共同点：它们表示某一个参数。 有时，你想同时操作多个参数，或者你并不知道会有多少参数传递进来。 在JavaScript里，你可以使用 arguments来访问所有传入的参数。
```
buildName(firstName: string, ...restOfName: string[]) {
  return firstName + " " + restOfName.join(" ");
}

let employeeName = buildName("Joseph", "Samuel", "Lucas", "MacKinzie");
```

##  泛型
- 软件工程中，我们不仅要创建一致的定义良好的API，同时也要考虑可重用性。 组件不仅能够支持当前的数据类型，同时也能支持未来的数据类型，这在创建大型系统时为你提供了十分灵活的功能。在像C#和Java这样的语言中，可以使用泛型来创建可重用的组件，一个组件可以支持多种类型的数据。 这样用户就可以以自己的数据类型来使用组件

- 方法
```
identity<T>(arg: T): T {
    return arg;
}
let output = identity<string>("myString");
```

- 类型
```
let myIdentity: <T>(arg: T) => T = identity;
```

- 接口
```
interface GenericIdentityFn {
    <T>(arg: T): T;
}

identity<T>(arg: T): T {
    return arg;
}

let myIdentity: GenericIdentityFn = identity;
```

- 泛型类
```
class GenericNumber<T> {
    zeroValue: T;
    add: (x: T, y: T) => T;
}

let myGenericNumber = new GenericNumber<number>();
myGenericNumber.zeroValue = 0;
myGenericNumber.add = function(x, y) { return x + y; };
```

##  枚举
- Up使用初始化为 1。 其余的成员会从 1开始自动增长。 换句话说， Direction.Up的值为 1， Down为 2， Left为 3， Right为 4。不初始化Up=1 则Up的值为 0， Down的值为 1等等。
```
enum Direction {
    Up = 1,
    Down,
    Left,
    Right
}
```

- 字符串枚举
```
enum Direction {
    Up = "UP",
    Down = "DOWN",
    Left = "LEFT",
    Right = "RIGHT",
}
```

- 异构枚举

```
enum BooleanLikeHeterogeneousEnum {
    No = 0,
    Yes = "YES",
}
```

## 迭代器和生成器
当一个对象实现了Symbol.iterator属性时，我们认为它是可迭代的。 一些内置的类型如 Array，Map，Set，String，Int32Array，Uint32Array等都已经实现了各自的Symbol.iterator。 对象上的 Symbol.iterator函数负责返回供迭代的值。

- for..of vs. for..in 语句
for..of会遍历可迭代的对象，调用对象上的Symbol.iterator方法。for..of和for..in均可迭代一个列表；但是用于迭代的值却不同，for..in迭代的是对象的 键 的列表，而for..of则迭代对象的键对应的值。
```
let list = [4, 5, 6];

for (let i in list) {
    console.log(i); // "0", "1", "2",
}

for (let i of list) {
    console.log(i); // "4", "5", "6"
}
```

##  模块
模块在其自身的作用域里执行，而不是在全局作用域里；这意味着定义在一个模块里的变量，函数，类等等在模块外部是不可见的，除非你明确地使用export形式之一导出它们。 相反，如果想使用其它模块导出的变量，函数，类，接口等的时候，你必须要导入它们，可以使用 import形式之一。
模块是自声明的；两个模块之间的关系是通过在文件级别上使用imports和exports建立的。

- 导出
  - 导出声明
  任何声明（比如变量，函数，类，类型别名或接口）都能够通过添加export关键字来导出
  
  ```
  export interface StringValidator {
    isAcceptable(s: string): boolean;
  }
  
  export const numberRegexp = /^[0-9]+$/;

  export class ZipCodeValidator implements StringValidator {
      isAcceptable(s: string) {
          return s.length === 5 && numberRegexp.test(s);
      }
  }
  ```
  - 导出语句
  ```
  export class ZipCodeValidator implements StringValidator {
      isAcceptable(s: string) {
          return s.length === 5 && numberRegexp.test(s);
      }
  }
  
  export { ZipCodeValidator };
  ```
  - 重新导出

  ```
  export class ParseIntBasedZipCodeValidator {
    isAcceptable(s: string) {
        return s.length === 5 && parseInt(s).toString() === s;
    }
  }

  // 导出原先的验证器但做了重命名
  export {ZipCodeValidator as RegExpBasedZipCodeValidator} from "./ZipCodeValidator";
  
  //一个模块可以包裹多个模块，并把他们导出的内容联合在一起通过语法：export * from "module"
  ```
- 导入
  - 导入一个模块中的某个导出内容
  ```
  import { ZipCodeValidator } from "./ZipCodeValidator";
  
  let myValidator = new ZipCodeValidator();
  
  //可以对导入内容重命名
  import { ZipCodeValidator as ZCV } from "./ZipCodeValidator";
  let myValidator = new ZCV();
  ```
  - 将整个模块导入到一个变量，并通过它来访问模块的导出部分
  ```
  import * as validator from "./ZipCodeValidator";
  let myValidator = new validator.ZipCodeValidator();
  ```
  - 默认导出
   ```
   //导出
   export default "123";
   //导入
   import num from "./OneTwoThree";
   //使用
   console.log(num); // "123"
   ```
##  模块规范 
- 尽可能在顶层导出 
- 如果只导出单个 class or function 使用 export default 
- 导入时请明确的列出导入的名字 
- 文件只有一个export class或export function （考虑使用export default）

