/*******************************************************************************
 * Copyright  (c) 2015-2016, WSO2.Telco Inc. (http://www.wso2telco.com) All Rights Reserved.
 * 
 * WSO2.Telco Inc. licences this file to you under  the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.wso2telco.dep.mediator.impl.wallet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wso2telco.dep.oneapivalidation.exceptions.CustomException;

public class WalletHandlerFactory {
	private static Log log = LogFactory.getLog(WalletHandlerFactory.class);
	
	    public static WalletHandler createWalletHandler(String resourceURL, WalletExecutor executor) {
	
	       WalletHandler walletHandler = null;
	        String transactionOperationStatus = null;
	        log.debug("createPaymentHandler -> Json string : " + executor.getJsonBody().toString());
	
	        String httpMethod = executor.getHttpMethod();
	
	        if (httpMethod.equalsIgnoreCase("post")) {
	            if (resourceURL.contains("payment")) {
	                log.debug("createWalletHandler -> Wallet API type : payment");
	                walletHandler = new WalletPaymentHandler(executor);
	
	            } else if (resourceURL.contains("refund")) {
	                walletHandler=new WalletRefundHandler(executor);
	
	            }
	        } else if (httpMethod.equalsIgnoreCase("get")) {
	            if (resourceURL.contains("/list")) {
	                log.debug("createWalletHandler -> Wallet API type : list");
	                walletHandler = new WalletListHandler(executor);
	
	            } else if (resourceURL.contains("/balance")) {
	                log.debug("createWalletHandler -> Wallet API type : balance");
	                walletHandler = new WalletBalanceHandler(executor);
	
	            } else {
	                log.debug("createWalletHandler -> API Type Not found");
	                throw new CustomException("SVC0002", "", new String[]{null});
	            }
	        } else {
	            log.debug("createPaymentHandler -> API Type Not found");
	            throw new CustomException("SVC0002", "", new String[]{null});
	        }
	
	        return walletHandler;
	    }
	
	    private static String nullOrTrimmed(String s) {
	        String rv = null;
	        if (s != null && s.trim().length() > 0) {
	            rv = s.trim();
	        }
	        return rv;
	    }
	
}
