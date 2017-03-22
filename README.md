# 507


How to install  
Run ./install.sh  
Add 507 Project into Eclipse/IntelliJ  
Add javasymbolsolver-core, javasymbolsolver-model, javasymbolsolver-logic to Eclipse/IntelliJ  
Run 507  
  
# Modified Library Files:  

* CPSC_507/javaparser/javaparser-core/src/main/java/com/github/javaparser/ast/body/CallableDeclaration.java
```java
public abstract String getSignature();
```

* CPSC_507/javaparser/javaparser-core/src/main/java/com/github/javaparser/ast/body/ConstructorDeclaration.java
```java
@Override
public String getSignature() {
  StringBuilder sb = new StringBuilder();
  sb.append(getName());
  sb.append("(");
  boolean firstParam = true;
  for (Parameter param : getParameters()) {
      if (firstParam) {
          firstParam = false;
      } else {
          sb.append(", ");
      }
      sb.append(param.getType().toString(prettyPrinterNoCommentsConfiguration));
  }
  sb.append(")");
  return sb.toString();
}
```

* CPSC_507/javaparser/javaparser-core/src/main/java/com/github/javaparser/ast/body/MethodDeclaration.java
```java
@Override
public String getSignature() {
  StringBuilder sb = new StringBuilder();
  sb.append(getName());
  sb.append("(");
  boolean firstParam = true;
  for (Parameter param : getParameters()) {
      if (firstParam) {
          firstParam = false;
      } else {
          sb.append(", ");
      }
      sb.append(param.getType().toString(prettyPrinterNoCommentsConfiguration));
      if (param.isVarArgs()) {
          sb.append("...");
      }
  }
  sb.append(")");
  return sb.toString();
}
```
