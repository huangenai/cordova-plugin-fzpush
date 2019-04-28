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
