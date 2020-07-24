#!/usr/bin/env bash
ELASTIC=elasticsearch:9200
INDEX=everhomesv3

curl -XDELETE http://$ELASTIC/$INDEX/_mapping/huqiDemo
curl -XPUT "http://$ELASTIC/$INDEX/_mapping/huqiDemo" -d '
{
    "huqiDemo": {
        "properties": {
            "id": {
                "type": "long"
            },
            "score": {
                "type": "long"
            },
            "level": {
                "type": "string",
                "index": "not_analyzed"
            },
            "name": {
                "type": "string",
                "index": "not_analyzed"
            },
            "family": {
                "type": "nested",
                "properties": {
                    "age": {
                        "type": "long"
                    },
                    "name": {
                        "type": "string",
                        "index": "not_analyzed"
                    }
                }
            },
            "createTime": {
                "type": "long"
            },
            "updateTime": {
                "type": "long"
            }
        }
    }
}
'
