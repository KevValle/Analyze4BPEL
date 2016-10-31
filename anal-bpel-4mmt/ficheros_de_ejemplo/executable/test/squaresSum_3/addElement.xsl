<stylesheet xmlns="http://www.w3.org/1999/XSL/Transform" xmlns:p="http://xml.netbeans.org/schema/squaresSum">

	<param name="newItem"/>

	<template match="p:elements">
		<copy>
			<copy-of select="p:element"/>
			<copy-of select="$newItem"/>
		</copy>
	</template>

</stylesheet>
