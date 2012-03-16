<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
    <xsl:output method="xml" version="2.0" encoding="utf-8" indent="yes"/>

    <xsl:template match="/">
        <map xmlns="urn:itba:it:ontologias:20121q:tutorial1">
	        <xsl:for-each select="/html//div[@class='contents-after-sidebar']//tr">
	           <entry>
	               <key><xsl:value-of select="th"/></key>
	               <value><xsl:value-of select="td"/></value>
	           </entry>
	        </xsl:for-each>
        </map>
    </xsl:template>
    <xsl:template match="script"></xsl:template>
</xsl:stylesheet>