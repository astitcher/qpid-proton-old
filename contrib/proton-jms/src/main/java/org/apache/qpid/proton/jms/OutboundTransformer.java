/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.qpid.proton.jms;

import javax.jms.Message;

/**
* @author <a href="http://hiramchirino.com">Hiram Chirino</a>
*/
public abstract class OutboundTransformer {

    JMSVendor vendor;
    String prefixVendor;

    String prefixDeliveryAnnotations = "DA_";
    String prefixMessageAnnotations= "MA_";
    String prefixFooter = "FT_";

    String messageFormatKey;
    String nativeKey;
    String firstAcquirerKey;
    String prefixDeliveryAnnotationsKey;
    String prefixMessageAnnotationsKey;
    String subjectKey;
    String contentTypeKey;
    String contentEncodingKey;
    String replyToGroupIDKey;
    String prefixFooterKey;

    private boolean useByteDestinationTypeAnnotations;

   public OutboundTransformer(JMSVendor vendor) {
        this.vendor = vendor;
        this.setPrefixVendor("JMS_AMQP_");
    }

    public abstract EncodedMessage transform(Message jms) throws Exception;

    public boolean isUseByteDestinationTypeAnnotations()
    {
        return useByteDestinationTypeAnnotations;
    }

    public void setUseByteDestinationTypeAnnotations(boolean useByteDestinationTypeAnnotations)
    {
        this.useByteDestinationTypeAnnotations = useByteDestinationTypeAnnotations;
    }

    public String getPrefixVendor() {
        return prefixVendor;
    }

    public void setPrefixVendor(String prefixVendor) {
        this.prefixVendor = prefixVendor;

        messageFormatKey = prefixVendor + "MESSAGE_FORMAT";
        nativeKey = prefixVendor + "NATIVE";
        firstAcquirerKey = prefixVendor + "FirstAcquirer";
        prefixDeliveryAnnotationsKey = prefixVendor + prefixDeliveryAnnotations;
        prefixMessageAnnotationsKey = prefixVendor + prefixMessageAnnotations;
        subjectKey =  prefixVendor +"Subject";
        contentTypeKey = prefixVendor +"ContentType";
        contentEncodingKey = prefixVendor +"ContentEncoding";
        replyToGroupIDKey = prefixVendor +"ReplyToGroupID";
        prefixFooterKey = prefixVendor + prefixFooter;

    }

    public JMSVendor getVendor() {
        return vendor;
    }

    public void setVendor(JMSVendor vendor) {
        this.vendor = vendor;
    }
}
