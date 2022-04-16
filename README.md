# Audit-Event-Processor

---

## Event Payload

```json
{
  "eventTime": "yyyy-mm-dd hh-mm-ss",
  "service": "MSA Service",
  "auditUser": "User PK",
  "event": "BREAD (Browse / Read / Edit / Add / Delete)",
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
  "event": "ADD",
  "eventModel": "Chat",
  "eventModelId": 1,
  "payload": {
    "message": "Hello World",
    "to": 1994
  }
}
```
