# 507


How to install (For now)
Run ./install.sh  
Add 507 Project into Eclipse/IntelliJ  
Add javasymbolsolver-core, javasymbolsolver-model, javasymbolsolver-logic to Eclipse/IntelliJ  
Run 507  
  
# Tracking locally modified Library Files:
* Integrated changes  
https://github.com/javaparser/javaparser/issues/882

Changed toReferenceType()  
```
		List<com.github.javaparser.ast.type.Type> superClassTypeParameters = classOrInterfaceType.getTypeArguments()
				.get();
		for (com.github.javaparser.ast.type.Type type : superClassTypeParameters) {
			if (type.toString().equals(this.getWrappedNode().getNameAsString())) {
				superClassTypeParameters.remove(type);
			}
		}
		List<Type> solvedTypeParameters = superClassTypeParameters.stream()
				.map(ta -> JavaParserFacade.get(typeSolver).convert(ta, ta)).collect(Collectors.toList());
		return new ReferenceTypeImpl(ref.getCorrespondingDeclaration().asReferenceType(), solvedTypeParameters,
typeSolver);
```
* Integrated changes  
https://github.com/javaparser/javasymbolsolver/commit/eba1cb8067a401c05e584c60a3031b72c0a008b3

