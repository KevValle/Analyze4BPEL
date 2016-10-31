<stylesheet xmlns="http://www.w3.org/1999/XSL/Transform" xmlns:p="http://xml.netbeans.org/schema/squaresSum">

	<param name="newVarItem"/>

	<template match="p:varianceElements">
		<copy>
			<copy-of select="p:element"/>
			<copy-of select="$newVarItem"/>
		</copy>
	</template>

</stylesheet>
