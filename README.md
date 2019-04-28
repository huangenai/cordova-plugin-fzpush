# TypeScript

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
function warnUser（）： void {
     console。log（“这是我的警告信息”）; 
}
```

### Null and Undefined
- undefined和null实际是有分别自己类型的命名undefined和null，他们两个并不是很有用。null，undefined是所有其他类型的子类型.但是基本不会给对象属性声明为undefined or null

### Never
- 不会有返回值。通常用于函数表达式或箭头函数表达式的返回类型抛出异常或永不返回的异常。

### Object
- object是一种类型的，不可以直接给类型为object的对象赋值原始类型。
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
- var let const
- let 
  - 块级作用域
  - 重定义及屏蔽
  - 先定义后使用
- const
  - 值不可变


##  变量声明规范
- var声明有些奇怪的作用域规则,请使用let or const 来代替var


## 接口
在TypeScript里，interface 是比较常用的。其作用是为给类型命名和给你的代码或第三方代码定义契约。

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

- 可选属性
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
let mySearch: SearchFunc;
mySearch = function(src: string, sub: string): boolean {
  let result = src.search(sub);
  return result > -1;
}
```

- 类类型
interface ClockInterface {
    currentTime: Date;
}

class Clock implements ClockInterface {
    currentTime: Date;
    constructor(h: number, m: number) { }
}

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
```
与C#或Java非常相似，不多做备注。
- 继承
- public，private,protected,readonly修饰符
- get set
- 静态属性
- 抽象类


let greeter = new Greeter("world");

补充：readonly vs const
- 判断该用readonly还是const的方法是看要把它做为变量使用还是做为一个属性。 做为变量使用的话用 const，若做为属性则使用readonly。

##  函数
例
```
myAdd(x: number, y: number):number{
   return x + y; 
}

let myAdd: (x: number, y: number) => number =function(x: number, y: number): number { return x + y; };

//可选参数
function buildName(firstName: string, lastName?: string) {
    if (lastName)
        return firstName + " " + lastName;
    else
        return firstName;
}

//默认初始化的参数
function buildName(firstName: string, lastName = "Smith") {
    return firstName + " " + lastName;
}

//剩余参数
function buildName(firstName: string, ...restOfName: string[]) {
  return firstName + " " + restOfName.join(" ");
}

let employeeName = buildName("Joseph", "Samuel", "Lucas", "MacKinzie");

```

##  泛型
- 软件工程中，我们不仅要创建一致的定义良好的API，同时也要考虑可重用性。 组件不仅能够支持当前的数据类型，同时也能支持未来的数据类型，这在创建大型系统时为你提供了十分灵活的功能。在像C#和Java这样的语言中，可以使用泛型来创建可重用的组件，一个组件可以支持多种类型的数据。 这样用户就可以以自己的数据类型来使用组件

例子
```
//方法
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

function identity<T>(arg: T): T {
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
