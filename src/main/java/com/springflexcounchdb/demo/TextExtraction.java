package com.springflexcounchdb.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/**
 * https://www.codegrepper.com/code-examples/java/replacing+string+with+dynamic+avalues+java
 * https://www.baeldung.com/java-remove-replace-string-part
 * 
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class TextExtraction {
	public static void main1(String[] args) {

//		String s = java.text.MessageFormat.format("I would like to have {0} KG of rice and {1} Litre of Milk. I only have ${2}, is this sufficient?",new String[]{"100","5","50"});
//		System.out.println(s);
		String finalCommand = "";
		String helmCommand = "helm install percona-operator {{ .attributes.operatorRepo }} -n {{ .env.namespace }} --skip-crds --kubeconfig\n"
				+ "            /mnt/{{ .attributes.targetcluster }} --set pxc.resources.limits.memory={{ product.flavour['memory'] }}\n"
				+ "            --set customBackup.nfsStorageclass={{ .attributes.storageClass }}";

		System.out.println("helmCommand actual command:: " + helmCommand);

		Attributes attributes = new Attributes("operatorRepoValue", "targetcluster", "storageClass");
		Flavour flavour = new Flavour("memoryValue");
		Product product = new Product(flavour);

		// using replace
		// finalCommand = replaceText(helmCommand, attributes, product);

		// using regex
		//finalCommand = regexMethod(helmCommand, attributes, product);
		
		// using utilsMethod
		finalCommand = utilsMethod(helmCommand, attributes, product);

		System.out.println("\n\n helmCommand result:: " + finalCommand);
	}

	public static String utilsMethod(String command, Attributes attributes, Product product) {

		command = "helm install percona-operator ${.attributes.operatorRepo} -n ${.env.namespace} --skip-crds --kubeconfig\n"
				+ "            /mnt/${.attributes.targetcluster} --set pxc.resources.limits.memory=${.product.flavour['memory']}\n"
				+ "            --set customBackup.nfsStorageclass=${.attributes.storageClass}";
		
		 // Build map
		 Map<String, String> valuesMap = new HashMap<>();
		 valuesMap.put(".attributes.operatorRepo", attributes.getOperatorRepo());
		 valuesMap.put(".attributes.targetcluster", attributes.getTargetcluster());
		 valuesMap.put(".attributes.storageClass", attributes.getStorageClass());
		 valuesMap.put(".product.flavour['memory']", product.getFlavour().getMemory());
		 valuesMap.put(".env.namespace", System.getenv("namespace"));

		 // Build StringSubstitutor
		 StringSubstitutor sub = new StringSubstitutor(valuesMap);

		 // Replace
		 String resolvedString = sub.replace(command);
		
		return resolvedString;
	}

	public static String regexMethod(String command, Attributes attributes, Product product) {

		String operatorRepo = "\\b{{ .attributes.operatorRepo }}\\b";
		String targetcluster = "\\b{{ .attributes.targetcluster }}\\b";
		String storageClass = "\\b{{ .attributes.storageClass }}\\b";
		String flavour = "\\b{{ product.flavour['memory'] }}\\b";
		String namespace = "\\b{{ .env.namespace }}\\b";

		String exactWordReplaced = RegExUtils.replaceAll(command, operatorRepo, attributes.getOperatorRepo());
		exactWordReplaced = RegExUtils.replaceAll(command, targetcluster, attributes.getTargetcluster());
		exactWordReplaced = RegExUtils.replaceAll(command, storageClass, attributes.getStorageClass());
		exactWordReplaced = RegExUtils.replaceAll(command, flavour, product.getFlavour().getMemory());
		exactWordReplaced = RegExUtils.replaceAll(command, namespace, System.getenv("namespace"));

		return exactWordReplaced;
	}

	public static String replaceText(String command, Attributes attributes, Product product) {

		// replaceAttributes
		String replaceAttributes = command.replace("{{ .attributes.operatorRepo }}", attributes.getOperatorRepo())
				.replace("{{ .attributes.targetcluster }}", attributes.getTargetcluster())
				.replace("{{ .attributes.storageClass }}", attributes.getStorageClass());

		// replaceProudctValues
		String replaceProudctValues = replaceAttributes.replace("{{ product.flavour['memory'] }}",
				product.getFlavour().getMemory());

		// replaceEnvironmentValues
//		@Value("${bar:default_value}")
//		private String myVariable;
//		 or will get System.getenv
		String finalCommand = replaceProudctValues.replace("{{ .env.namespace }}", System.getenv("namespace"));
		return finalCommand;
	}
}

@AllArgsConstructor
@Setter
@Getter
class Attributes {

	private String operatorRepo;

	private String targetcluster;
	private String storageClass;

}

@AllArgsConstructor
@Setter
@Getter
class Product {

	private Flavour flavour;

}

@AllArgsConstructor
@Setter
@Getter
class Flavour {

	private String memory;

}
