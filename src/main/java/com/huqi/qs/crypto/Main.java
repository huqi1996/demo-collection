package com.huqi.qs.crypto;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    public static String computeSignature(Map<String, String> params, String secretKey) {
        assert (params != null);
        assert (secretKey != null);

        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            byte[] rawKey = Base64.decodeBase64(secretKey);

            SecretKeySpec keySpec = new SecretKeySpec(rawKey, "HmacSHA1");
            mac.init(keySpec);

            List<String> keyList = new ArrayList<String>();
            CollectionUtils.addAll(keyList, params.keySet().iterator());
            Collections.sort(keyList);
            for (String key : keyList) {
                mac.update(key.getBytes("UTF-8"));
                String val = params.get(key);
                if (val != null && !val.isEmpty()) {
                    mac.update(val.getBytes("UTF-8"));
                }
            }

            byte[] encryptedBytes = mac.doFinal();
            String signature = Base64.encodeBase64String(encryptedBytes);

            return signature;
        } catch (InvalidKeyException e) {
            throw new InvalidParameterException("Invalid secretKey for signing");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException for HmacSHA1", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException for UTF-8", e);
        }
    }

    public static boolean verifySignature(Map<String, String> params, String secretKey, String signatureToVerify) {
        String signature = computeSignature(params, secretKey);

        if (signature.equals(signatureToVerify)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {

        /**
         * {
         *     "appKey": "529d0816-9fd5-4cac-a5ce-bf1ab21218e7",
         *     "nonce": "1559550064",
         *     "timestamp": "1547020251000",
         *     "flowCaseId": "6788",
         *     "signature": "gT0LmfGVU/H8iYbHUnhlsaOjSG8=",
         *     "approveData": [
         *         {
         *             "nodeName": "审批2",
         *             "approveTime": "2019-06-03 15:40:22",
         *             "userName": "2300",
         *             "approveRemark": "7777",
         *             "requestStatus": "批准"
         *         },
         *         {
         *             "nodeName": "审批2",
         *             "approveTime": "2019-06-03 15:36:00",
         *             "userName": "6159",
         *             "approveRemark": "3333",
         *             "requestStatus": "批准"
         *         },
         *         {
         *             "nodeName": "审批2",
         *             "approveTime": "2019-06-03 15:17:16",
         *             "userName": "2300",
         *             "approveRemark": "",
         *             "requestStatus": "提交"
         *         }
         *     ]
         * }
         */

        Map<String, String> params = new HashMap<>();
        params.put("appKey", "529d0816-9fd5-4cac-a5ce-bf1ab21218e7");
        params.put("timestamp", "1547020251000");
        params.put("nonce", "1559612989");
        params.put("flowCaseId", "6819");

        params.put("approveData[0].nodeName", "创建");
        params.put("approveData[0].userName", "2300");
        params.put("approveData[0].approveTime", "2019-06-03 14:31:51");
        params.put("approveData[0].approveResult", "提交");
        params.put("approveData[0].approveRemark", "无");
//        params.put("approveData[1].nodeName", "审批2");
//        params.put("approveData[1].userName", "6159");
//        params.put("approveData[1].approveTime", "2019-06-03 15:36:00");
//        params.put("approveData[1].requestStatus", "批准");
//        params.put("approveData[1].approveRemark", "3333");
//        params.put("approveData[2].nodeName", "审批2");
//        params.put("approveData[2].userName", "2300");
//        params.put("approveData[2].approveTime", "2019-06-03 15:17:16");
//        params.put("approveData[2].requestStatus", "提交");

        String secretKey = "fFNvPKsnSC4fyRDuL73cq0nBxadYKux5l19P85aQ5o3l623VBsLOhwtA0k8G2/skW13qzrKC6buk82oOnCjIcg==";
        String signature = computeSignature(params, secretKey);
        System.out.println(signature);
        System.out.println(verifySignature(params, secretKey, signature));
    }
}
