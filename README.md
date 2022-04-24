# Audit-Event-Processor

---

# Audit-Event-Processor Module

```
├── consumer
│   ├── AuditEventConsumerConfiguration.java
│   ├── adapter
│   │   ├── in
│   │   │   └── AuditEventConsumer.java
│   │   └── out
│   │       └── persistence
│   │           └── eventstore
│   │               ├── EventStoreEntity.java
│   │               ├── EventStorePersistenceAdapter.java
│   │               ├── EventStoreRepository.java
│   │               └── converter
│   │                   └── EventStoreEntityConverter.java
│   └── port
│       └── out
│           └── EventStoreDataProvider.java
├── core
│   ├── adapter
│   │   └── out
│   │       └── persistence
│   │           └── audit
│   │               ├── AuditEntity.java
│   │               └── AuditingEntityEventListener.java
│   └── domain
│       ├── enums
│       │   ├── AuditEventType.java
│       │   ├── AuditFieldsEnum.java
│       │   └── EventPublishType.java
│       ├── model
│       │   └── AuditEvent.java
│       └── utils
│           ├── AuditEventFactory.java
│           └── AuditFieldExtractor.java
└── producer
    ├── AuditEventProducerConfiguration.java
    └── adapter
        └── out
            └── InternalAuditEventPublisher.java
```

# How To Use


# Event Payload

```json
{
  "service": "MSA Service",
  "eventTime": "yyyy-mm-dd hh-mm-ss",
  "eventUser": "User PK",
  "eventType": "CREATED | UPDATED | DELETED",
  "publishType": "IMMEDIATELY | RESERVE | REPEAT",
  "domain": "Domain Entity",
  "domainId": "Domain Entity PK",
  "payload": {
    "something": "Serialized Entity Domain"
  }
}
```

# Sample Payload
```json

{
  "service": "chat-application",
  "eventTime": "1994-12-24 00:00:00",
  "eventUser": 0,
  "eventType": "CREATED",
  "publishType": "IMMEDIATELY",
  "domain": "Chat",
  "domainId": 1,
  "payload": {
    "message": "Hello World",
    "to": 1994
  }
}
```
