<?xml version = "1.0"?>
<xsl:stylesheet xmlns:xsl = "http://www.w3.org/1999/XSL/Transform" version = "1.0">

    <xsl:output method = "html" indent = "yes"/>
    <xsl:template match = "/">
        <html>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match = "posts">
        <table border = "1" width = "100%">
            <xsl:for-each select = "post">
                <tr>
                    <td>
                        <i><xsl:value-of select = "postTitle"/></i>
                    </td>

                    <td>
                        <xsl:value-of select = "postUser"/>
                    </td>

                    <td>
                        <xsl:value-of select = "message"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

</xsl:stylesheet>