<stylesheet xmlns="http://www.w3.org/1999/XSL/Transform" xmlns:p="http://examples.bpelunit.org/MetaSearch" version="1.0">

	<param name="currentItem"/>

	<template match="p:MetaSearchProcessResponse">
		<copy>
			<copy-of select="@*|node()"/>
			<copy-of select="$currentItem"/>
		</copy>
	</template>

</stylesheet>
