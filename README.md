# Audit-Event-Processor


![image](https://user-images.githubusercontent.com/26922008/163697344-7ed20514-ddda-41ff-9b6e-76dc7ea60924.png)


## Event Payload

```json
{
  "eventTime": "yyyy-mm-dd hh-mm-ss",
  "service": "MSA Service",
  "auditUser": "User PK",
  "eventType": "CUD (CREATED/UPDATED/DELETED)",
  "eventModel": "Domain Entity",
  "eventModelId": "Domain Entity PK",
  "payload": {
    "something": "Serialized Entity Domain"
  }
}
```

### Sample Payload
```json

{
  "eventTime": "1994-12-24 00:00:00",
  "service": "chat-application",
  "auditUser": 0,
  "eventType": "CREATED",
  "eventModel": "Chat",
  "eventModelId": 1,
  "payload": {
    "message": "Hello World",
    "to": 1994
  }
}
```
