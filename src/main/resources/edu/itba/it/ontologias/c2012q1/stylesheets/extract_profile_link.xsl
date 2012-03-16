<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" version="2.0" encoding="utf-8" indent="yes"/>

    <!-- 
        Transformación que extrae el valor del 
            <link rel="me" href="http://www.blogger.com/profile/10546461224000881156" />
        Que tipicamente tienen las paginas de blogger. 
     -->
	    <xsl:template match="/">
            <links xmlns='urn:itba:it:ontologias:20121q:tutorial1'>
	         <xsl:for-each select="/html/head/link[@rel='me']">
	            <link>
	                <xsl:attribute name="href">
	                    <xsl:value-of select="@href"/>
	                </xsl:attribute>
	            </link>
	          </xsl:for-each>
            </links>
	    </xsl:template>
    <xsl:template match="script"></xsl:template>
</xsl:stylesheet>