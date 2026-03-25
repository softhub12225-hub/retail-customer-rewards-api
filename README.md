# Customer Rewards API

Spring Boot REST service that calculates retail loyalty points per transaction and aggregates **points by customer and calendar month**, plus **total over the program**, for a **single three-calendar-month reward period** (configurable, enforced for all requests).

## Rules (per transaction)

- **$0.01–$50.00:** 0 points  
- **$50.01–$100.00:** 1 point per whole dollar in this band (fractional dollars truncated)  
- **Above $100.00:** 2 points per whole dollar above $100 (fractional dollars truncated after doubling)

Example: **$120** → \(2 \times 20\) + \(1 \times 50\) = **90 points**.

## Requirements

- JDK 17+
- Maven 3.9+ (or use `./mvnw` / `mvnw.cmd` after setting `JAVA_HOME`)

## Run

```bash
./mvnw spring-boot:bootRun
```

Windows (PowerShell):

```powershell
$env:JAVA_HOME = "C:\Path\to\jdk-17"
.\mvnw.cmd spring-boot:bootRun
```

## Reward program period

All transactions must fall within the inclusive dates below (defaults match the demo dataset). The configured range **must span exactly three calendar months** or the application will not start.

```properties
rewards.period.start-date=2024-01-01
rewards.period.end-date=2024-03-31
```

`POST /api/rewards/calculate` returns **400** if any `purchaseDate` is outside this window.

## API

| Method | Path | Description |
|--------|------|----------------|
| `GET` | `/api/rewards/demo` | Returns rewards for the built-in demo dataset (inside the configured period) |
| `POST` | `/api/rewards/calculate` | Accepts transactions **within the reward period**; returns the same summary shape |

### POST body

```json
{
  "transactions": [
    {
      "customerId": "alice",
      "amountUsd": 120.00,
      "purchaseDate": "2024-01-15"
    }
  ]
}
```

### Response shape

```json
{
  "customers": [
    {
      "customerId": "alice",
      "pointsByMonth": {
        "2024-01": 90,
        "2024-02": 49
      },
      "totalPoints": 139
    }
  ]
}
```

Customers are sorted by `customerId`. Months use ISO `YYYY-MM` keys.

## Tests

```bash
./mvnw test
```
