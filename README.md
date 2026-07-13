                    Client

                       │

                HTTP Request

                       │

                 ┌──────────┐
                 │   API    │
                 └──────────┘
                       │
                 Produce Event
                       │
                    Kafka
                       │
                 Consume Event
                       │
               ┌────────────┐
               │   Worker   │
               └────────────┘
                       │
                  Send Email
                  Generate PDF
                  Resize Image
                  dll.