# AERO

A centralised app connecting:
- Samsung health
- Google fit / Google Health
- Strava

Aero has 2 main purposes:
- Replacing UI with Aero's minimal material UI
- Combining data alongside telemetry data gathered by the app, to produce precise values by adjusting a weighting factor:
  For example:
  
| Provider | Steps | Bias |
|----------|------:|-----:|
| Google Health | 5000 | 50% |
| Aero | 6000 | 30% |
| Samsung Health | 5300 | 20% |

      **Total: 5360 Steps**

  
