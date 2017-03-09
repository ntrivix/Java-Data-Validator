# Java-Data-Validator
Easily validate your inputs and manage validation errors


#Usage example

Initialize validator

```java
Validator v;
v = Validator.validate()
			//.setDefaultInputErrorHandler(TextualPanelErrorHandler.class)
			.add(field1, "Email", new String[] { "mail", "required" }, additionalErrorPanel1)
			.add(field2, "Number", new String[] { "size:5,7", "number" }, new String[] {}, additionalErrorPanel2)
			.add(area1, "Tekst", new String[] { "size:10,150" })
			.add(checkBox, "Terms", new String[]{"confirmed"})
            .dynamicallyValidate(true)
			.errorsContainer(formErrors);
```

Validate
```java
if (v.validateAll().isValid())
  return true;
else
  return false;
```
