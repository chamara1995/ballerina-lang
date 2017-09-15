/*
* Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.ballerinalang.net.http.actions;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeEnum;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.natives.connectors.BalConnectorCallback;
import org.ballerinalang.net.http.Constants;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.transport.http.netty.message.HTTPCarbonMessage;

/**
 * {@code Patch} is the PATCH action implementation of the HTTP Connector.
 */
@BallerinaAction(
        packageName = "ballerina.net.http",
        actionName = "patch",
        connectorName = Constants.CONNECTOR_NAME,
        args = {
                @Argument(name = "c",
                        type = TypeEnum.CONNECTOR),
                @Argument(name = "path", type = TypeEnum.STRING),
                @Argument(name = "req", type = TypeEnum.STRUCT, structType = "Request",
                        structPackage = "ballerina.net.http")
        },
        returnType = {@ReturnType(type = TypeEnum.STRUCT, structType = "Response",
                structPackage = "ballerina.net.http")},
        connectorArgs = {
                @Argument(name = "serviceUri", type = TypeEnum.STRING)
        }
)
public class Patch extends AbstractHTTPAction {

    private static final Logger logger = LoggerFactory.getLogger(Patch.class);

    @Override
    public BValue execute(Context context) {

        logger.debug("Executing Native Action : Patch");

        try {
            // Execute the operation
            return executeAction(context, createCarbonMsg(context));
        } catch (Throwable t) {
            throw new BallerinaException("Failed to invoke 'patch' action in " + Constants.CONNECTOR_NAME
                    + ". " + t.getMessage(), context);
        }
    }

    @Override
    public void execute(Context context, BalConnectorCallback callback) {

        if (logger.isDebugEnabled()) {
            logger.debug("Executing Native Action (non-blocking): {}", this.getName());
        }
        try {
            // Execute the operation
            executeNonBlockingAction(context, createCarbonMsg(context));
        } catch (Throwable t) {
            throw new BallerinaException("Failed to invoke 'patch' action in " + Constants.CONNECTOR_NAME
                                         + ". " + t.getMessage(), context);
        }
    }

    protected HTTPCarbonMessage createCarbonMsg(Context context) {
        HTTPCarbonMessage cMsg = super.createCarbonMsg(context);
        cMsg.setProperty(Constants.HTTP_METHOD, Constants.HTTP_METHOD_PATCH);
        return cMsg;
    }

}
