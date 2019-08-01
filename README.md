# Software Engineer Challenge
1. Coding task:

    1. Code is in `src\main`, tests in `src\test`.
    2. You can run the tests with `./gradlew test`

2. Design Question: Design A Google Analytic like Backend System.

All our analytics data can be represented with a timestamp + a key-value pair of strings.

To avoid excessive networking, clients should be able to submit batch analytics by supplying a series of
events in a single request.

For data serialization, we can use Protobufs to save on bandwidth and support both easily updating our
protocol specification and generating idiomatic client libraries.

A super-simple batched event message can then be defined as follows:

```
message BatchedAnalytics {
  message Event {
    required google.protobuf.Timestamp timestamp = 1;
    required string name = 2;
    required string value = 3;
  }

  repeated Event events = 1;
}
```

For our services, let's assume we're writing them in Go (although any sensible server-side language
could be used!).

First we'll need an analytics endpoint (let's name it AnalyticsService) to receive batched events from our
clients, unpack them, and handle saving both the original events and saving the structured data to a
database. Google's BigQuery is an ideal backing DB for this use-case, and also supports integrations with
most popular reporting tools.

We can run our AnalyticsService with GCP Cloud Load Balancing to support the billions of writes needed.
Each instance of AnalyticsService will receive analytics events and store the raw data in GCS buckets.

Once per hour, another service (let's call it 'BigQueryTransferService') will take this data and import
into BigQuery for querying.

As for backups, we can persist the raw data in GCS for as long as we want to pay for it, allowing us to
recreate the BigQuery tables from scratch should we need to.

For ensuring up-time, we'd need to have our own ops monitoring pipeline set up and monitoring the health
of our services. Alerting will need to tell us a given metric is outside our SLO. 
