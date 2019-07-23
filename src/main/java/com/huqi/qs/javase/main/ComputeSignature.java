package com.huqi.qs.javase.main;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author huqi 20190513
 */
public class ComputeSignature {
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
         *     "timestamp": 1558661559247,
         *     "nonce": "123456",
         *     "flowCaseId": "6699",    // 左邻流程ID，在左邻调用OA时提供
         *     "approveData": [
         *         {
         *             "nodeName": "节点1",   // 象屿审批节点名称
         *             "userName": "张三",   // 审批用户名称
         *             "approveTime": "2019-05-13 16:25:49",  // 审批时间
         *             "approveResult": "同意",   // 审批结果
         *             "approveRemark": "审批通过"  // 审批意见备注
         *         },
         *         {
         *             "nodeName": "节点2",
         *             "userName": "李四",
         *             "approveTime": "2019-05-16 14:55:37",
         *             "approveResult": "驳回",
         *             "approveRemark": "缺少资料，需要补全"
         *         }
         *     ],
         *     "signature": "9n3Mao%2fnf0xRgMWqXFItfZ0Irw8%3d"
         * }
         */

        Map<String, String> params = new HashMap<>();
        params.put("appKey", "529d0816-9fd5-4cac-a5ce-bf1ab21218e7");
        long timestamp = 1558661559247L;
        params.put("timestamp", "1.558661559247E12");
        params.put("nonce", "123456");
        params.put("flowCaseId", "6699");

        params.put("approveData[0].nodeName", "节点1");
        params.put("approveData[0].userName", "张三");
        params.put("approveData[0].approveTime", "2019-05-13 16:25:49");
        params.put("approveData[0].approveResult", "同意");
        params.put("approveData[0].approveRemark", "审批通过");
        params.put("approveData[1].nodeName", "节点2");
        params.put("approveData[1].userName", "李四");
        params.put("approveData[1].approveTime", "2019-05-16 14:55:37");
        params.put("approveData[1].approveResult", "驳回");
        params.put("approveData[1].approveRemark", "缺少资料，需要补全");

        String secretKey = "fFNvPKsnSC4fyRDuL73cq0nBxadYKux5l19P85aQ5o3l623VBsLOhwtA0k8G2/skW13qzrKC6buk82oOnCjIcg==";
        String signature = computeSignature(params, secretKey);
        System.out.println(signature);
        System.out.println(verifySignature(params, secretKey, signature));

        System.out.println(String.format("5555", 11L));
    }
}
