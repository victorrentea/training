
package victor.training.ws.responsibility.impl.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import victor.training.ws.responsibility.impl.dto.ResponsibilityXml;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responsibility" type="{http://training.victor/ws/responsibility/domain/xmlbean/v1}Responsibility"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "responsibility"
})
@XmlRootElement(name = "getUserResponsibilityResponse")
public class GetUserResponsibilityResponse {

    @XmlElement(required = true)
    protected ResponsibilityXml responsibility;

    /**
     * Gets the value of the responsibility property.
     * 
     * @return
     *     possible object is
     *     {@link ResponsibilityXml }
     *     
     */
    public ResponsibilityXml getResponsibility() {
        return responsibility;
    }

    /**
     * Sets the value of the responsibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponsibilityXml }
     *     
     */
    public void setResponsibility(ResponsibilityXml value) {
        this.responsibility = value;
    }

}
