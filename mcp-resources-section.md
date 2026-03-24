# MCP Server — Resources Section (ICICI Lombard Complete Home Protect Policy)

## Overview

The Resources section defines **static knowledge files** that the MCP server exposes to LLM clients (e.g., Claude Desktop). These files serve as the authoritative reference corpus so the LLM can answer user queries about ICICI Lombard's Complete Home Protect Policy accurately, without hallucination.

Each resource below should be stored as a **structured Markdown or JSON file** inside `src/main/resources/static/` in the Spring Boot project and exposed via MCP resource endpoints.

---

## Resource Directory Layout

```
src/main/resources/static/
├── policy/
│   ├── 01-policy-overview.md
│   ├── 02-definitions.md
│   ├── 03-insured-events.md
│   ├── 04-home-building-cover.md
│   ├── 05-home-contents-cover.md
│   ├── 06-additional-covers-optional.md
│   ├── 07-add-on-covers.md
│   ├── 08-section-wise-covers.md
│   ├── 09-general-exclusions.md
│   ├── 10-claims-procedure.md
│   ├── 11-policy-conditions.md
│   ├── 12-renewal-cancellation-termination.md
│   ├── 13-terrorism-cover.md
│   ├── 14-personal-accident-details.md
│   ├── 15-grievance-ombudsman.md
│   └── 16-premium-and-sum-insured.md
├── reference/
│   ├── section-index.json
│   ├── coverage-limits-quick-ref.json
│   ├── depreciation-schedule.json
│   ├── personal-accident-benefits-table.json
│   └── faq-common-scenarios.md
└── metadata/
    ├── policy-metadata.json
    └── contact-info.json
```

---

## Resource #1: `policy/01-policy-overview.md`

**URI:** `policy://icici-lombard/home-protect/overview`
**Name:** Policy Overview & Structure
**Description:** High-level overview of the ICICI Lombard Complete Home Protect Policy including product identity, regulatory details, policy structure, and how sections are organized.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# ICICI Lombard Complete Home Protect Policy — Overview

## Product Identity
- **Insurer:** ICICI Lombard General Insurance Company Limited
- **IRDAI Registration No.:** 115
- **CIN:** L67200MH2000PLC129408
- **UIN:** IRDAN115RP0013V02202122
- **Product Name:** Complete Home Protect Policy

## Contact Information
- **Toll Free:** 1800 2666
- **Alternate No:** 86552 22666 (chargeable)
- **Email:** customersupport@icicilombard.com
- **Website:** www.icicilombard.com
- **Mailing Address:** 601 & 602, 6th Floor, Interface 16, New Linking Road, Malad (West), Mumbai - 400 064
- **Registered Office:** ICICI Lombard House, 414, Veer Savarkar Marg, Near Siddhi Vinayak Temple, Prabhadevi, Mumbai 400 025

## Policy Structure
The policy is divided into three parts:

### Part I — Policy Schedule
Contains insured details, intermediary details, policy period, hypothecation details, property details, sum insured breakdowns, premium computation, clauses/conditions/warranties, and co-insurance details.

### Part II — Policy Wordings
Contains 18 coverage sections plus add-on covers. Section 1 (ICICI Bharat Griha Raksha) is **mandatory**. At least one additional section must be opted. Under Section 1, the insured can opt for Cover 1(a) Home Buildings, Cover 1(b) Home Contents, or both.

### Part III — Standard Terms and Conditions
Contains policyholder obligations, renewal provisions, cancellation/termination rules, claims procedure, and other operational details.

## Section Index
| Section | Coverage Name |
|---------|--------------|
| Section 1 | ICICI Bharat Griha Raksha (Mandatory) |
| — 1(a) | Home Buildings Cover |
| — 1(b) | Home Contents Cover |
| Section 2 | Additional Living Expenses |
| Section 3 | Mechanical and/or Electrical Breakdown |
| Section 4 | Garden Cover |
| Section 5 | Home Contents — All Risk Cover |
| Section 6 | Loss of Contents Whilst in Transit |
| Section 7 | Loss of Cash Whilst in Transit |
| Section 8 | Cover for Specified Articles |
| Section 9 | Personal Liability |
| Section 10 | Theft Cover for Valuables |
| Section 11 | Break-in Cover |
| Section 12 | Recreational Bicycle Cover |
| Section 13 | Loss of Documents |
| Section 14 | Lock & Key Replacement |
| Section 15 | Personal Accident |
| Section 16 | Fixed Glass and Sanitary Fittings |
| Section 17 | Pet Cover |
| Section 18 | Permanent Relocation Cover |

## Add-On Covers (under Section 1)
- EMI Protector
- Accidental Damage Cover
- Tenant's Liability Insurance
- Landscaping Cost
- Damage to Utility Systems
- Incidental Costs
- Inadvertent Omission
- Minor Acquisitions

## Optional Covers (under Clause E)
- Cover for Valuable Contents on Agreed Value Basis
- Personal Accident Cover (₹5,00,000 per person for insured/spouse death due to insured peril)

## Add-On Covers (under Section 2)
- Hotel Stay
- Brokerage for Alternate Accommodation

## Add-On Covers (under Section 11)
- Students Contents
- Security Upgrade
- Precautionary Repairs

## Additional Benefits (Non-Claim)
1. **Pest Control:** Free pest control service from authorized provider, once every 3 consecutive years of policy engagement.
2. **Fire Hazard / Electrical Hazard / Structural Stability Inspection:** Free inspection once every 5 consecutive years of policy engagement.

## Insurance Contract Components
The policy contract consists of:
a. The Policy Document
b. The Policy Schedule
c. Any Endorsements
d. Any Add-ons purchased
e. Proposals and declarations made by/on behalf of the insured

## Key Principle: Waiver of Underinsurance
Underinsurance does NOT apply to Complete Home Protect Policy. If the Sum Insured (based on info provided) is less than actual value at risk, the difference will not reduce claim payment.
```

---

## Resource #2: `policy/02-definitions.md`

**URI:** `policy://icici-lombard/home-protect/definitions`
**Name:** Policy Definitions & Key Terms
**Description:** All defined terms used in the policy with their specific meanings as per the policy wordings.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Policy Definitions

All terms below are capitalized in the policy document and carry specific legal meaning.

## Core Definitions

### Accident
A sudden, unforeseen, and unexpected physical event caused by external, violent and visible means.

### Bank
A bank or any financial institution.

### Carpet Area
1. **Main building unit:** Net usable floor area, excluding external walls, service shafts, exclusive balcony/verandah, and open terrace areas, but INCLUDING internal partition walls of the residential unit.
2. **Enclosed structure on same site:** Net usable floor area of that structure.
3. **Balcony, verandah, terrace, parking, or any enclosed structure** that is part of the premises.

### Commencement Date
The date and time from which insurance cover begins, as shown in the Policy Schedule.

### Cost of Construction
Amount required to construct the Home Building at the Commencement Date, calculated as:
- **Residential structure (incl. fittings/fixtures):** Carpet Area (sq. metres) × Rate of Cost of Construction at Commencement Date (as declared by insured and accepted by insurer)
- **Additional structures:** Based on prevailing rate of Cost of Construction at Commencement Date

### Endorsement
A written amendment to the Policy (additions, deletions, modifications, exclusions, or conditions) that may change terms or scope of the original policy.

### Home Contents
Articles or things in the Home NOT permanently attached or fixed to the structure. Consist of:
- **General Contents**, and/or
- **Valuable Contents**

### General Contents
All contents of household use in the Home. Examples: furniture, electronic items, antennae, solar panels, water storage equipment, kitchen equipment, electrical equipment (including wall-fitted), clothing, apparel, and similar items.

### Valuable Contents
Items such as jewellery, silverware, paintings, works of art, antique items, curios, and similar items.

### Insured / You / Your
The Person(s) who has/have purchased Insurance Cover under this Policy.

### Insured Property
The Home Building and/or Home Contents, or any item of property covered by the Policy.

### Kutcha Construction
Building(s) having walls and/or roofs of wooden planks, thatched leaves, grass/hay of any kind, bamboo, plastic cloth, asphalt, canvas, tarpaulin, and the like.

### Pucca Construction
Construction other than Kutcha Construction.

### Market Value
New replacement value of a similar item less depreciation. Specific depreciation rules:
- **Domestic appliances & electronics** (refrigerator, washing machine, microwave, TV, cassette recorders, audio systems, VCR, VCD, DVD etc.): Present day replacement cost of similar new item at flat rate of **15% per annum** subject to maximum of **75%** depreciation.
- **Personal computers:** Present day replacement cost (schedule continues in policy).

### Policy Period
Period from effective date/time shown in Schedule, terminating at midnight on expiry date shown in Schedule, OR on termination/cancellation under Clause G(III), whichever is earlier.

### Policy Schedule
Document accompanying the Policy giving insured details and insurance cover details.

### Premium
Amount paid for insurance. Policy Schedule shows premium amount for the Policy Period plus all taxes and levies.

### Salvage
Amount assessed that the damaged asset will fetch in the open market. This amount is deducted from the claim amount.

### Spouse
Wife or husband of the insured.

### Sum Insured
Amount shown as Sum Insured in the Policy Schedule. Represents insurer's maximum liability for each cover/part of cover and for each loss.

### Total Loss
Situation where Insured Property or item is:
- Completely destroyed, OR
- Lost beyond retrieval or repair, OR
- Cost of repairing exceeds Sum Insured for that item or in total, OR
- Damaged beyond repair.

### Your Home Building
A building consisting of a residential unit, having an enclosed structure and a roof, basement (if any), used as a dwelling place.

### We / Us / Our / Insurer
ICICI Lombard General Insurance Company.

## Section-Specific Definitions

### Burglary (Section 11)
Any theft following upon actual forcible and violent entry of and/or exit from the premises by the person(s) committing such theft. Includes housebreaking.

### Theft (Section 11)
An act in which property belonging to insured is taken by 3rd party without Insured's consent.

### Hold Up (Section 7)
When the insured is threatened by any weapon and there exists a possibility of actual physical harm.

### Lawn Belongings (Section 4)
Furniture, swings, statues, fountain and machines & equipment used in development and maintenance of the garden.

### Domestic Appliances (Section 3)
Air Conditioners, TV(s), Tape recorders, Radios, Refrigerator(s), Washing Machine(s), Micro Wave Oven(s), Mixers/Grinders/Food Processor(s), Oven(s), Cooking Range(s), and similar household appliances kept in the home building.

### High Value Electronics (Section 3)
Music System(s), DVD Players, Home Computer(s) (including peripherals like Printer, PC Cameras, Speakers, and all equipment connected/linked to Home Computer), Laptops, Home Theatre Systems, and similar equipment.

### Domesticated Animal / Pet (Section 17)
Any species of dogs and cats that have been selectively bred and genetically adapted to live and breed in a tame condition over generations alongside humans. Includes Indigenous Origin, Cross-bred, and Exotic breeds.

### Family / Family Member
The proposer and any one or more of:
- Legally wedded spouse
- Parents and Parents-in-law
- Children (naturally or legally adopted)

Note: For Section 11 (Break-in Cover), Family Member also includes any person who normally resides with the insured and is related by blood, marriage, or adoption.

### Equated Monthly Instalment / EMI (Add-on)
Amount of monthly payment required to repay principal and interest by the insured as mentioned in loan agreement between Financial Institution(s) and insured.

### Financial Institution (Add-on)
Institution as defined under Section 45I of Reserve Bank of India Act 1934, including a non-banking financial company.

## Personal Accident Definitions (Section 15)

### Capital Sum Insured
Monetary amount shown against Insured Person in the schedule.

### Insured Person
Person named as Insured person in the policy schedule.

### In-Patient Care
Treatment requiring hospital stay for more than consecutive/uninterrupted 24 hours for the covered event.

### Hospital / Nursing Home
Institution established for in-patient care and day care treatment, registered under Clinical Establishments Act, 2010 or equivalent, OR meeting all minimum criteria:
- Qualified nursing staff round the clock
- At least 10 in-patient beds (towns < 10 lakh population) or 15 beds (other places)
- Qualified medical practitioner(s) round the clock
- Fully equipped operation theatre
- Maintains daily patient records accessible to insurer

### Medical Expenses
Expenses necessarily and actually incurred for medical treatment on account of Accident, on Medical Practitioner's advice, no more than standard charges in that locality.

### Medical Practitioner
Person with valid registration from Medical Council of India/State/Council for Indian Medicine/Homeopathy, entitled to practice within jurisdiction. Includes physician, specialist, anaesthetist, surgeon. Excludes insured person and family members.

### Loss of Limbs
a. Physical separation of one or more limbs at or above wrist/ankle level due to injury (including medically necessary amputation). Must be permanent without surgical correction chance.
b. Total and irreversible loss of functional use of a limb for at least 90 days from onset, with no reasonable medical hope of improvement.
Exclusion: Loss from self-inflicted injury, alcohol, or drug abuse.

### Physical Separation
- Hand: Severance at or above the wrists
- Foot: Severance at or above the ankle

### Permanent Total Disablement
Total and irrecoverable losses resulting solely and directly from Injury, within 12 months of Accident date.

### Temporary Total Disablement
Loss from accidental Injury within Policy Period that completely incapacitates insured from ANY employment/occupation. Payable for max 104 weeks.

### Child
Dependent child/children (including adopted/step) of Insured Person, ages 2-18 years (23 years if full-time student), unmarried, receiving majority support from Insured Person.

### Cumulative Bonus
Any increase in Sum Insured / Mallus granted by insurer without associated premium increase.

### Qualified Nurse
Person with valid registration from Nursing Council of India or any State Nursing Council.
```

---

## Resource #3: `policy/03-insured-events.md`

**URI:** `policy://icici-lombard/home-protect/insured-events`
**Name:** Insured Events (Clause B) — What Is Covered & Not Covered
**Description:** Complete list of 14 insured events with their respective exclusions under Clause B.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Clause B: Insured Events

The policy covers physical loss, damage, or destruction to Insured Property caused by the following unforeseen events during the Policy Period.

## Event 1: Fire
- **Covered:** Physical loss/damage from fire
- **Not Covered:** Loss caused by burning of Insured Property by order of any Public Authority

## Event 2: Explosion or Implosion
- **Covered:** Physical loss/damage from explosion or implosion
- **Not Covered:** No specific exclusions

## Event 3: Lightning
- **Covered:** Physical loss/damage from lightning
- **Not Covered:** No specific exclusions

## Event 4: Earthquake, Volcanic Eruption, or Other Convulsions of Nature
- **Covered:** Physical loss/damage from earthquake, volcanic eruption, or other convulsions of nature
- **Not Covered:** No specific exclusions

## Event 5: Storm, Cyclone, Typhoon, Tempest, Hurricane, Tornado, Tsunami, Flood and Inundation
- **Covered:** Physical loss/damage from any of these natural events
- **Not Covered:** No specific exclusions

## Event 6: Subsidence, Landslide, Rockslide
- **Covered:** Subsidence of land on which Home Building stands, landslide, rockslide
- **Not Covered:**
  a. Normal cracking, settlement or bedding down of new structures
  b. Settlement or movement of made-up ground
  c. Coastal or river erosion
  d. Defective design or workmanship or use of defective materials
  e. Demolition, construction, structural alterations or repair of any property, or groundworks or excavations

## Event 7: Bush Fire, Forest Fire, Jungle Fire
- **Covered:** Physical loss/damage from bush/forest/jungle fire
- **Not Covered:** No specific exclusions

## Event 8: Impact Damage
- **Covered:** Damage caused by impact of, or collision caused by, any external physical object (e.g., vehicle, falling trees, aircraft, wall, etc.)
- **Not Covered:** Damage caused by pressure waves from aircraft or other aerial/space devices travelling at sonic or supersonic speeds

## Event 9: Missile Testing Operations
- **Covered:** Physical loss/damage from missile testing operations
- **Not Covered:** No specific exclusions

## Event 10: Riot, Strikes, Malicious Damages
- **Covered:** Physical loss/damage from riot, strikes, malicious damages
- **Not Covered:**
  a. Temporary or permanent dispossession, confiscation, commandeering, requisition or destruction by order of government or any lawful authority
  b. Temporary or permanent dispossession of Home by unlawful occupation by any person

## Event 11: Acts of Terrorism
- **Covered:** As per Terrorism Clause attached to the policy
- **Not Covered:** Exclusions and excess as per Terrorism Clause attached

## Event 12: Bursting or Overflowing of Water Tanks, Apparatus and Pipes
- **Covered:** Physical loss/damage from bursting/overflowing of water tanks, apparatus, pipes
- **Not Covered:** No specific exclusions

## Event 13: Leakage from Automatic Sprinkler Installations
- **Covered:** Physical loss/damage from sprinkler leakage
- **Not Covered:**
  a. Repairs or alterations in the Home or building where Home is located
  b. Repairs, removal or extension of any sprinkler installation
  c. Defects in construction known to the insured

## Event 14: Theft (Post-Insured-Event)
- **Covered:** Theft within 7 days from occurrence of and proximately caused by any of the above Insured Events (1-13)
- **Not Covered:**
  a. Theft of any article or thing outside the Home
  b. Theft of any article or thing attached from outside of outer walls or roof, unless securely mounted
```

---

## Resource #4: `policy/04-home-building-cover.md`

**URI:** `policy://icici-lombard/home-protect/home-building-cover`
**Name:** Clause C — Home Building Cover
**Description:** Complete details of Home Building Cover including what's covered, building definition, use restrictions, sum insured rules, payment calculation, loss of rent, and alternative accommodation.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Clause C: Home Building Cover (Section 1a)

## 1. What Is Covered
- Physical loss, damage, or destruction of Home Building due to any Insured Event (Clause B)
- Architect's, surveyor's, consulting engineer's fees
- Cost of removing debris (Clause C(5)(f))
- Loss of Rent and Rent for Alternative Accommodation (Clause C(6))

## 2. Definition of Home Building

### What the Home Building IS:
- A building consisting of a residential unit with enclosed structure and roof, basement (if any), used as dwelling place

### What the Home Building INCLUDES:
- Fixtures and fittings permanently attached to floor, walls or roof (fixed sanitary fittings, electrical wiring, other permanent fittings)
- Additional structures on the same site used as part of the Home Building:
  - Garage, domestic out-houses for residence, parking spaces/areas
  - Compound walls, fences, gates, retaining walls, internal roads
  - Verandah, porch, and the like
  - Septic tanks, bio-gas plants, fixed water storage units/tanks
  - Solar panels, wind turbines, AC systems, central heating (if NOT included in Home Contents Cover)
- Any other structure shown in the Policy Schedule

### What the Home Building DOES NOT include:
- Contents of the Home

## 3. Use Restrictions

### Will Pay:
- Home Building used for residence of insured and family, OR tenant, licensee, or employee

### Will NOT Pay:
- Home Building used as a holiday home, or for lodging and boarding
- Home Building (or part of it) used for non-residential purposes
  - **Exception:** Used both for residence AND earning livelihood (self-employed), OR office temporarily shifted to Home Building due to lockdown/closure ordered by public authority

## 4. Sum Insured Rules

### Base Sum Insured:
- Prevailing Cost of Construction at Commencement Date (declared by insured, accepted by insurer)
- Represents maximum payable for Total Loss

### Automatic Escalation (for policies > 1 year):
- Sum Insured increases by **10% per annum** on each policy anniversary
- No additional premium required
- Maximum escalation: **100% of original Sum Insured**

### Daily Escalation (annual policies):
- Automatic daily increase = 1/365th of 10% of Sum Insured at Commencement Date

### Restoration of Sum Insured:
- After claim payment, Sum Insured is restored to full original amount
- Insured must pay proportionate premium for unexpired period from date of loss
- Insurer can deduct this premium from net claim amount
- Exception: Does not apply if policy terminates per Clause G(III)(3)(b)

## 5. What Is Paid

### Partial Damage:
- Cost to repair Home Building to condition substantially the same as at time of damage
- Insured must first spend on repairs, then claim reimbursement
- Claim calculated on actual Carpet Area (not exceeding declared Carpet Area)
- Maximum payout = Sum Insured shown in Schedule for Home Building Cover

### Total Loss:
- Full Sum Insured of the Home Building

### Additional Structure Destroyed:
- Cost of Construction of the additional structure

### Additional Expenses (beyond main Sum Insured):
- **Architect/Surveyor/Consulting Engineer fees:** Up to **5%** of claim amount
- **Debris removal costs:** Up to **2%** of claim amount

## 6. Loss of Rent and Rent for Alternative Accommodation

### Eligibility:
- Home Building is not fit for living due to physical loss from an Insured Event
- Only if physical damage claim under Home Building Cover is accepted

### For Tenants Paying Higher Rent for Alternative:
- Insurer pays the **difference** between rent for alternative accommodation and rent of original Home Building

### Accommodation Standard:
- Alternative must NOT be superior to original Home Building
- Must be in the same city as original Home Building

### Calculation:
Sum Insured for Loss of Rent (as declared) × Period necessary for repairs ÷ Loss of Rent Period opted for

### Duration:
- Reasonable time required for repairs
- Maximum: **3 years** from date Home Building becomes unfit
- Certificate from architect or local authority required to prove unfitness

### Prerequisite:
- Claim for loss of rent accepted ONLY if physical damage claim under Home Building Cover is also accepted
```

---

## Resource #5: `policy/05-home-contents-cover.md`

**URI:** `policy://icici-lombard/home-protect/home-contents-cover`
**Name:** Clause D — Home Contents Cover
**Description:** Details of Home Contents Cover including coverage scope, sum insured, built-in cover, and claim settlement options.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Clause D: Home Contents Cover (Section 1b)

## 1. What Is Covered
- Physical loss, damage, or destruction of **General Contents** of the Home caused by an Insured Event (Clause B)
- **Valuable Contents are NOT covered** unless the optional cover for Valuable Contents is purchased

## 2. Sum Insured

### Amount:
- As shown in the Policy Schedule
- Maximum payable if Home Contents are completely destroyed/lost

### Built-in Cover (when both Home Building and Home Contents opted):
- General Contents automatically covered for **20% of Sum Insured for Home Building Cover**
- Maximum built-in cover: **₹10 Lakh (Rupees Ten Lakh)**
- For higher Sum Insured: Declare in Proposal Form and pay additional premium

### Only Home Contents Cover Purchased:
- Declare Sum Insured for General Contents in Proposal Form

### Adequacy:
- Sum Insured must be enough to cover cost of replacement of General Contents

### Valuable Contents:
- Must opt for Optional Cover for Valuable Contents (Clause E(1)(a))

### Restoration of Sum Insured:
- Same rules as Home Building Cover — restored to full after claim, with proportionate premium

## 3. What Is Paid

### Options (at insurer's choice):
i. **Reimburse** cost of repairs to condition substantially same as at time of damage, OR
ii. **Pay** cost of replacing item with same or similar item, OR
iii. **Repair** the damaged item to condition substantially same as at time of damage

### Limits:
- Maximum = Sum Insured for Home Contents Cover in Schedule
- Any sub-limits for specific items/categories/groups shown in Schedule apply
```

---

## Resource #6: `policy/06-additional-covers-optional.md`

**URI:** `policy://icici-lombard/home-protect/additional-covers-optional`
**Name:** Clause E — Optional & Additional Covers
**Description:** Valuable Contents on Agreed Value Basis and Personal Accident Cover details.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Clause E: Additional Covers

## 1. Optional Covers

### a. Cover for Valuable Contents on Agreed Value Basis (under Home Contents Cover)

#### Valuation:
- Value agreed upon by insured and insurer based on valuation certificate submitted by insured
- **Valuation certificate waived** if:
  - Sum Insured opted for is up to **₹5 Lakh**
  - AND individual item value does not exceed **₹1 Lakh**

#### Claim Settlement:
- **If physically damaged by Insured Event:** Insurer pays cost of repairing the item(s)
- **If Total Loss:** Insurer pays Sum Insured shown in Schedule for the Valuable item(s)
  - Sub-limits for specific items/categories/groups apply as shown in Schedule

#### Important Note:
- Loss to only ONE item of a pair or set does NOT constitute loss/damage to the entire pair or set

### b. Personal Accident Cover

#### Trigger:
- An insured peril that caused damage to Home Building and/or Home Contents ALSO results in death of insured or spouse

#### Compensation:
- **₹5,00,000 (Rupees Five Lakh) per person**

#### Continuation:
- If insured dies, Personal Accident cover continues for the spouse until policy expiry

## 2. Add-ons
- Chosen by insured from offerings under this product
- Purchased add-ons mentioned in Policy Schedule
- Relevant clauses/endorsements attached to the Policy
```

---

## Resource #7: `policy/07-add-on-covers.md`

**URI:** `policy://icici-lombard/home-protect/add-on-covers`
**Name:** Add-On Covers — EMI Protector, Accidental Damage, Tenant's Liability, and More
**Description:** Detailed coverage of all add-on covers available under Section 1, including EMI Protector, Accidental Damage, Tenant's Liability, Landscaping, Utility Systems, Incidental Costs, Inadvertent Omission, and Minor Acquisitions.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Add-On Covers (Section 1 — ICICI Bharat Griha Raksha)

## EMI Protector

### What Is Covered:
- Equated Monthly Instalments (EMI) payable to financial institution(s) upon loss/damage due to insured peril

### Trigger Conditions:
1. Loss/damage due to insured peril AND actual repair/replacement time exceeds **1 month**
2. Insurer liability limited to maximum **3 months EMI** or Sum Insured, whichever is less
3. Subject matter of loss must be **mortgaged** with financial institution(s) as stated in schedule

### Exclusions:
- Any consequential loss or charges (late payment charges, documentation charges, etc.)

### Documentation Required:
- EMI Schedule certified by financer
- EMI payment track record for last 1 year preceding date of loss

### Limits:
- **Limit of Liability:** ₹50 Lacs (maximum)
- **Indemnity Period:** 3 months
- **Maximum Indemnity per month:** ₹20 Lacs

---

## Accidental Damage Cover

### What Is Covered:
- Property insured destroyed or damaged by **accidental external means** during schedule period
- Indemnity = value of property at time of destruction/damage

### Exclusions:
1. Caused by:
   a. Corrosion, rust, wet/dry rot, shrinkage, evaporation, weight loss, dampness, dryness, marring, scratching, vermin, insects
   b. Change in temperature, colour, flavour, texture, finish
   c. Joint leakage, weld failure, cracking, fracturing, collapse, overheating of boilers, economizers, superheaters, pressure vessels, connected steam/feed piping

2. Damage to belts, ropes, chains, rubber tyres, dies, moulds, blades, cutters, knives, exchangeable tools, engraved/impression cylinders/rolls, glass/porcelain/ceramics objects, operating media (lubricating oil, fuel, catalyst, refrigerant, dowtherm), felts, endless conveyor belts/wires, sieves, fabrics, heat-resisting/anti-corrosive linings, non-metal parts (except insulating material), non-metallic lining/coating of metal parts
   - **Exception:** If caused by fire, lightning, riot, strike, malicious damage, storm, tempest, flood, inundation

3. Burglary, theft, and/or attempts thereat

4. Breakage, cracking, scratching of crockery, glass, cameras, binoculars, lenses, sculptures, curios, pictures, musical instruments, sports gear, fragile/brittle items

5. Loss covered more specifically elsewhere in the policy

6. Property in transit

7. Damage covered under Policy Schedule (Material Damage) covers

8. Damage excluded under Policy Schedule (Material Damage) covers

9. Caused by:
   - Collapse or cracking of buildings
   - Shortage in supply/delivery of materials, or shortage due to clerical/accounting error
   - Any wilful act or wilful negligence by insured or person acting on insured's behalf

---

## Tenant's Liability Insurance

### What Is Covered:
- All sums insured becomes legally liable to pay following damage to:
  - Home Building occupied by insured, OR
  - Surrounding third-party property
- Consequent upon insured's occupation as a tenant

### Conditions:
i. **No liability** for any Home Building or portion sub-let by insured
ii. Insured must **not breach** any contractual obligation with landlord or other tenants that affects insurer's interests
iii. Claims limited to Sum Insured in Policy Schedule/Certificate of Insurance

### Initial Indemnity Rule:
- If landlord has effected insurance on insured's behalf, or insured is entitled to indemnity from other sources, those become "initial indemnity"
- Amount paid by initial indemnity is deducted from this policy's indemnity
- If initial indemnity exceeds this policy's limit, no payment under this clause

---

## Landscaping Cost

### What Is Covered:
- Reasonable cost of remaking, reconstituting, redesigning and purchasing as necessary to replace internal landscape grounds and gardens following Damage

---

## Damage to Utility Systems

### What Is Covered:
- Expenses necessarily and reasonably incurred to locate and access any part of the utility system to repair damages from leakage/overflow/short circuit, etc.

### NOT Covered:
- Cost of repair of the damaged utility (appliance) itself

### Scope:
- Limited to: Electricity distribution, cooking gas, and AC system within insured premises

### Liability Limit:
- **2.5% of total Sum Insured** for one or multiple events during policy period

---

## Incidental Costs

### What Is Covered:
- If loss/damage is payable under base policy, insurer also pays incidental costs associated with insured property
- Up to fixed percentage of admissible claim amount, as specified in Schedule

### Calculation:
- Based on admissible claim amount under base policy **after** all applicable deductions and/or **before** applying policy excess

---

## Inadvertent Omission

### What Is Covered:
- Property inadvertently omitted from Proposal Form is deemed insured within policy terms
- Limit: **10% of total Sum Insured**
- Subject to premium payment on all such property from policy inception

### Conditions:
- Insured must declare full details to insurer immediately upon becoming aware of omission
- No liability for building, machinery, plant, or other contents while otherwise insured
- "Other contents" does NOT include stocks of any kind

---

## Minor Acquisitions

### What Is Covered:
a. Home Contents acquired/operated/held after policy inception and not in Schedule
b. Additions/extensions to insured Home Contents after policy inception
c. Increase in New Replacement Value from such acquisitions must not exceed percentage of total Sum Insured specified in Schedule

### Conditions:
- Insured must advise insurer within **3 months** of minor acquisitions
- Additional premium may be required if acquisitions exceed Schedule limit

### Exclusions:
- Bullion, unset precious stones, curios, works of art, manuscripts, plans, drawings, securities, obligations, documents, stamps, coins, paper money, cheques, books of accounts, business books, computer system records, jewelleries, motor vehicles
```

---

## Resource #8: `policy/08-section-wise-covers.md`

**URI:** `policy://icici-lombard/home-protect/section-wise-covers`
**Name:** Sections 2–18 Coverage Details
**Description:** Detailed coverage, exclusions, conditions, and limits for all optional sections (2 through 18).
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Sections 2–18: Detailed Coverage

---

## Section 2: Additional Living Expenses

### Trigger:
- Home building damaged/destroyed by Accident during Policy Period AND becomes unfit for occupation
- Certificate from local municipal/statutory authority required

### Sub-Covers:

#### a) Boarding Expense for Pets
- Actual, reasonable expenses to board pets in alternate accommodation (if pets not permitted in temporary accommodation)
- **Max per month:** ₹30,000 or as specified in Schedule

#### b) Immediate Expenses
- Emergency clothing and toiletry items due to inability to access insured home
- **Max:** ₹50,000 or as specified in Schedule

#### c) Cost of Hiring Household Goods
- Essential furniture and household goods for alternative accommodation
- Terminates when home is fit or max indemnity period expires
- **Max per month:** ₹50,000 or as specified in Schedule

#### d) Expenses Towards Temporary Resettlement
- Packing, unpacking, transportation of possessions to alternative accommodation within same city
- **NOT covered:**
  - Loss/damage to Contents during packing/loading/transporting/unloading/installing
  - Expenses without actual bills/receipts/cash memos

### Family Definition (for this section):
Proposer + legally wedded spouse, parents/parents-in-law, children (natural/legally adopted)

### Additional Covers under Section 2:

#### Hotel Stay
- Additional expenses for hotel accommodation as interim accommodation
- **Indemnity period:** Limited to **15 days** while premises remain inhabitable
- Proof: Surveyor recommendation that premises are un-tenantable
- Must involve actual physical damage to building making it inhabitable
- Does NOT cover entry barred by strikers/demonstrators
- **Per day limit:** Up to 0.05% of Building/Structure Eligible Sum Insured, max **₹15,000/day**
- Available to tenant and owner-occupant

#### Brokerage for Alternate Accommodation
- Reasonable brokerage payment for obtaining alternative accommodation on rent
- Dwelling must be rendered unfit by insured peril (Clause B)
- Supported by valid brokerage receipt
- **Max:** ₹50,000 or one month rent of alternate accommodation, whichever is less

---

## Section 3: Mechanical and/or Electrical Breakdown Cover

### What Is Covered:
- Mechanical and/or electrical breakdown of **Domestic Appliances** and **High Value Electronics**
- Items must be specifically declared item-wise with individual values in the Schedule
- Items must be part of Contents in the home building

### Exclusions:
a. Loss/damage for which manufacturer or supplier is responsible (warranty or otherwise)
b. Appliances/equipment **older than 10 years** as on date of loss (unless specifically accepted)
c. Transport cost of damaged appliance/equipment to repair shop and back

### Basis of Indemnity:
- Items must be declared at **replacement value** (similar new items)
- **Repairable:** Actual cost of repairs (max = Sum Insured for that item)
- **Repair cost exceeds Market Value OR item totally damaged/destroyed:** Pay Market Value or Sum Insured, whichever is less

---

## Section 4: Garden Cover

### What Is Covered:
- Loss/damage to Lawn Belongings, trees, shrubs, or plants
- While in open and within dwelling boundaries
- Due to insured perils during Policy Period

### Exclusions:
a. Loss/damage by animals, wildlife, birds, insects, vermin, fungus, frost
b. Natural decay of trees, shrubs, plants, lawns
c. Mechanical and/or electrical breakdown

---

## Section 5: Home Contents — All Risk Cover

### What Is Covered:
- General Home Contents covered against ALL loss/damage due to and resulting from Accident
- Subject to general exclusions

### Exclusions (in addition to general):
2. Mechanical and/or electrical breakdown
3. Loss of Valuables by theft (unless specifically covered)
4. Loss/damage to General Home Content from:
   - 4.1 Its own fermentation
   - 4.2 Breakage, cracking, scratching of Crockery, Mobiles, Cameras, Binoculars, Sculptures, Curios, Paintings, Works of Art, Musical Instruments, Toys, Sports Gear, glassware items (unless specifically covered)

### Important Note:
- Home Content All Risk Cover is **independent** of Break-in Cover
- If insured opts for Section 5, Break-in Cover (Section 11) shall NOT be opted, and vice versa

---

## Section 6: Loss of Contents Whilst in Transit

### What Is Covered:
- Loss/damage to Contents while in transit from home building to new accommodation within India
- Transit by air, rail, or road on account of Accident
- Valid until property reaches alternative accommodation (including customary transhipment)

### Conditions:
1. Transit must commence within Policy Period
2. Maximum **3 transits** to max **1 new accommodation** during Policy Period
3. All transits covered for max **30 days** only
4. Prior written notice required (mode of conveyance, packing details, Lorry Receipt/Airway Bill)

### Exclusion:
- Loss/damage solely attributable to **insufficiency of packing**

---

## Section 7: Loss of Cash Whilst in Transit

### What Is Covered:
- Loss of money (coins/notes) by hold-up, robbery, or theft
- While money is in insured's possession, being conveyed from bank(s) or ATM to home building

### Conditions:
- Loss must occur within **6 hours** from time of withdrawal
- Insured must inform Police as soon as possible and obtain FIR

### Definition: Hold Up
- Threatened by any weapon with possibility of actual physical harm

---

## Section 8: Cover for Specified Articles

### What Is Covered:
- Loss/damage on account of Accident to specified items (with individual values in Schedule)
- Items being carried as personal baggage **outside** the insured home building

### Exclusions:
i. Mechanical and/or electrical breakdown of insured items
ii. Loss/damage from leakage, spilling, or explosion within personal baggage

### Basis of Indemnity:

#### 1. Items Other Than Valuables and Fine Arts:
- **Repairable:** Actual cost of repairs
- **Repair cost > Market Value OR totally damaged/destroyed:** Market Value at date of loss

#### 2. Valuables:
- Market Value of items
- For Valuables excluding precious stones: Limited to Market Value of metals only
- No additional value for making, decorating, or incidental charges
- All Valuables of individual value > ₹50,000 AND all precious stones (regardless of value): Must have Valuation certificate from professional valuer

#### 3. Fine Arts:
- **Partial damage (repairable/restorable):** Actual cost of repair/restoration
- **Repair/restoration cost > 75% of agreed value:** Treated as Total Loss
- **Total Loss:** Sum Insured as specified in Schedule
- Fine arts of individual value > ₹25,000: Must have Valuation certificate from professional valuer

#### 4. Pair & Set:
- Liability limited to proportionate value of lost/damaged part(s)
- No reference to special value as part of pair/set

---

## Section 9: Personal Liability

### What Is Covered:
Sums insured becomes legally liable to pay, including litigation expenses (with prior written consent), due to events **occurring in the home building**:

1. **Death or bodily injury** to any person other than insured or family, due to Accident
   - Covers legal compensation and litigation expense for third party
   - Covers medical expense for resident employees/domestic staff if:
     - Injury/death occurs within scope of employment
     - AND employee not eligible for workers' compensation

2. **Damage to property** of person other than insured, family, or domestic staff, due to Accident

### Family Definition:
Proposer + legally wedded spouse, parents/parents-in-law, children (natural/legally adopted)

### Exclusions:
- Death/injury/damage caused while person (or insured/family/staff) is under influence of drugs, alcohol, or intoxication
- Actions for damage outside jurisdiction of India
- Liability under agreement (unless would have applied otherwise)
- Any wilful or malicious act
- Transmission of communicable disease by insured or household member

### Domestic Staff Definition:
Employee/worker whose duties relate to the insured residence premise

### Liability Limit:
- Sum Insured for any one Accident or series from one event/cause, AND for all Accidents during Policy Period

---

## Section 10: Theft Cover for Valuables

### What Is Covered:
- Theft of valuable contents from home building
- Subject to coverage, Deductible, and terms in the Policy

---

## Section 11: Break-in Cover

### What Is Covered:
- Loss/damage caused by break-in, burglary, and/or attempted break-in/burglary to insured's contents

### Burglary Definition:
Theft following actual forcible and violent entry of and/or exit from the premises. Includes housebreaking.

### Liability Details:

a. **Damage due to burglary:** Extends to actual cost of repair/replacement of locks, damage to door/windows

b. **Jewellery, gold ornaments, silver articles, precious stones:**
   - Coverage is only against **burglary inside home premise** (NOT theft)

c. **Jewellery/gold/silver/precious stones:**
   - Applicable to insured and immediate family
   - Extends to items in Bank vault(s)
   - Items with individual value > ₹1,00,000: Must be specifically declared and noted by company
   - Settlement: Value of items in premises AND bank vaults collectively considered for condition of average
   - Liability limited to **inherent value of metal or precious stones** only (excludes making/decorating charges)

d. **Deductible excess** as specified in Schedule for all claims in a particular year

### Exclusions:
- Loss/damage where household member, business staff, or person lawfully in premises is involved (directly/indirectly)
- Loss of livestock, motor vehicles, pedal cycles, money, securities, stamp/coin collections, curios, sculptures, bullion, deeds, bonds, financial instruments, business books, manuscripts, rare books, ATM/debit/credit cards (unless previously declared and accepted)
- Loss while premises **unoccupied for > 30 consecutive days** (unless declared to insurer)
- Illegally acquired/stored property or property subject to forfeiture
- Money/property from safe using insured's key (unless key obtained by assault/violence/threat)
- General contents theft (unless specifically covered)

### Additional Covers under Section 11:

#### Students Contents
- Up to **25% of Home Contents value** in Schedule
- Loss/damage due to fire, burglary & theft
- Family member's Contents while living away for full-time education in India

#### Security Upgrade
- Up to **₹50,000** to upgrade security (alarms, locks) following a valid burglary claim

#### Precautionary Repairs
- Reasonable expenses up to **₹1,00,000** for necessary repairs to protect residence against further loss after a covered loss
- Does NOT increase coverage amount

---

## Section 12: Recreational Bicycle Cover

### What Is Covered:
1. Repair/replacement costs for bicycle owned by insured or family, from unforeseen sudden physical loss (max = Sum Assured per bicycle per Policy Period)
2. Legal liability for property damage or death/bodily injury to third parties arising from bicycle accident (max ₹30,000 for all claims in Policy Period)

### Bicycle Definition:
Pedal cycles, tricycles, scooters (non-motor vehicle), manually driven items (NOT motor/electrically driven)

### Exclusions:
a. Use for hire/reward or outside India
b. Overloading, strain, mechanical breakdown
c. Theft of accessories (unless bicycle also stolen)
d. Competition, racing, pace making

### Loss Settlement:
a. **Repairable:** Actual cost of restoration
b. **Total Loss:** Replacement cost up to Sum Assured

### Special Condition:
If left unattended, bicycle must be properly locked and secured.

---

## Section 13: Loss of Documents Cover

### What Is Covered:
- Reimbursement of actual expenses for replacement of:
  - Title deeds, Passport, Driving License, Work Permit, Residence Permit, or other official ID documents
- In case of loss by accident within India and temporarily outside India (up to 90 days per Policy Period)
- Limited to Sum Insured in Schedule

### Covered Causes:
i. Fire and Allied perils including earthquake (Clause B)
ii. Burglary, housebreaking, hold-up including theft
iii. Robbery, waylaying, snatching

### Exclusions:
i. Theft not reported to police within **24 hours** of awareness
ii. Left unattended/forgotten in public place, transport, hotel, apartment
iii. From private place/vehicle unless in locked room/apartment/vehicle with forcible entry
iv. Delay, confiscation, or detention by customs/police/public authorities
v. General Exclusions

---

## Section 14: Lock & Key Replacement

### What Is Covered:
- Up to **₹25,000** for replacing locks if keys lost or stolen (for Residence in Policy Schedule)
- OR up to **₹25,000** for replacing lost/stolen home building keys (limited to locksmith cost for new key)

### Lock Out Reimbursement:
- Up to **₹5,000** for locksmith cost if locked out of home building or vehicle due to theft of keys

### Rental Car Reimbursement:
- Up to **₹5,000** for reasonable rental car cost if vehicle keys lost/stolen and retrieval takes 24 hours

### Exclusions:
1. Costs outside "What We Cover" section
2. Lost/stolen keys for Home Building other than insured Home Building
3. Keys to vehicles not owned for personal use

### Duties After Loss:
1. Call 1800-2666 or written intimation within **24 hours** of discovering loss
2. File police report within **24 hours**
3. Return claims form and documents including police reports, receipts within **3 days** of original claim

---

## Section 16: Fixed Glass and Sanitary Fittings

### What Is Covered:
- Accidental breakage of fixed glass and sanitary fittings in the Home
- Cost of repair or replacement of damaged items

### Also Covered:
1. Damage to frame/framework following glass breakage
2. Cost of tinting, lettering, painting, embossing, silvering, ornamental work on replacement glass (if included in Sum Insured)
3. Accidental damage to home contents caused by breakage of glass/sanitary fittings (up to ₹5,000 per Policy Period unless otherwise agreed)

### Exclusions:
1. **Excess of ₹500** for each and every claim
2. Breakage during removal, alteration, repairs
3. Disfiguration, scratching, damage not extending through entire thickness
4. Breakage of items not completely/securely fixed
5. Consequential damage (except Item 3 above)

### Special Provision:
- Sum Insured for each item must equal replacement cost by new item of same kind
- Acts of Terrorism covered

---

## Section 17: Pet Cover

### Eligible Pets:
Dogs and cats (Indigenous, Cross-bred, and Exotic breeds) — domesticated, for companionship only (not income-earning)

### What Is Covered:

#### 1. Veterinary Expenses
- Pet injured by accident inside home premise, robbery, dacoity, terrorism, or poisoning by third party (not by insured/family/employee)
- Treatment must be by qualified Veterinary Doctor

#### 2. Death of Pet
- Due to robbery, dacoity, terrorism, accident inside home premise, or poisoning by third party within **30 days** of accident
- Up to **₹5,000** for burial/electric cremation costs (with proof and death certificate/post-mortem from Vet)

#### 3. Theft of Pet
- **Lost/stolen for 5+ continuous days:**
  - Up to 25% of Sum Insured or ₹25,000 (whichever less) for advertising and reward
- **Permanently lost/stolen for 90+ continuous days (despite advertising/reward):**
  - Pet's valuation at time of purchase/adoption, up to Sum Insured

#### 4. Third Party Liability
- Legal liability for bodily injury/property damage/sickness/death of third party due to pet
- Includes legal expenses/costs for defending claims (up to Schedule limit)

### Special Conditions:
a. Must be sole owner at commencement (cover ceases on sale/permanent transfer)
b. Proper care and attention at all times
c. Immediate vet consultation at own expense after accident
d. Police complaint within **5 days** for theft; take all recovery measures
e. Pet must be sound health and free from illness/disease at commencement
f. For death claims: Prove death doesn't fall under exclusions; submit Death certificate

### Exclusions:
1. Euthanasia or post-mortem charges
2. Animal in quarantine/shelter/not solely owned
3. Partial or total disabilities or any disease
4. Intentional killing by government/authority/person
5. Castration/spaying medical expenses
6. Death from:
   a. Surgery (unless by qualified vet, necessitated solely by accident)
   b. Malicious/wilful injury by insured or family
   c. Confiscation by government/authority
7. Third Party Liability if: Injured party is insured's family/resident/paid caretaker/trainer; or Vets/pet trainers/kennel employees/breeders/pet shop owners in course of profession
8. Lost/Stolen Cover: Reward to household members, caretakers, or the thief/accomplice; claims without proper signed receipts

---

## Section 18: Permanent Relocation Cover

### Trigger:
- Total loss at premises from Insured Event (Clause B)
- Insured opts NOT to reconstruct/reinstate or retain damaged dwelling
- Insured permanently relocates and abandons premise to insurer (including all rights and right to reconstruct)

### Claim Settlement Basis:

#### Option 1: Ready Reckoner Basis
- Amount = Ready Reckoner value (for Property Tax and Stamp Duty, issued by State Revenue Department for that locality at date of loss) **MINUS** reinstatement cost or Sum Insured (whichever is lower)

#### Option 2: Registration Value Basis
- Amount = Registration value of Sale Deed agreement value **MINUS** reinstatement cost or Sum Insured (whichever is lower)

### Payout:
- As per limit specified in Schedule (including land cost)
```

---

## Resource #9: `policy/09-general-exclusions.md`

**URI:** `policy://icici-lombard/home-protect/general-exclusions`
**Name:** Clause F — General Exclusions
**Description:** Universal exclusions that apply across ALL covers under the policy.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Clause F: General Exclusions (All Covers)

The insurer does NOT cover losses and expenses for loss/damage/destruction of Insured Property directly or indirectly caused by:

1. **Deliberate/Wilful Acts:** Your deliberate, wilful, or intentional act or omission, or of anyone on your behalf, or with your connivance.

2. **War & Related:** War, invasion, act of foreign enemy, hostilities or war-like operations (declared or not), civil war, mutiny, civil commotion amounting to popular rising, military rising, rebellion, revolution, insurrection, military or usurped power.

3. **Nuclear:** Ionising radiation or contamination by radioactivity from nuclear fuel or nuclear waste from combustion, or radioactive/toxic/explosive/hazardous properties of any explosive nuclear assembly or nuclear component.

4. **Pollution/Contamination:** Unless:
   i. Pollution/contamination itself resulted from an Insured Event, OR
   ii. An Insured Event itself results from pollution/contamination

5. **Electrical/Electronic Damage:** Loss/damage/destruction to any electrical/electronic machine, apparatus, fixture, or fitting by over-running, excessive pressure, short-circuiting, arcing, self-heating, or leakage of electricity from whatever cause (including lightning). **Applies ONLY to the particular machine** so affected.

6. **Bullion & Documents:** Loss/damage to bullion, unset precious stones, manuscripts, plans, drawings, securities, obligations, documents of any kind, coins, paper money, cheques, vehicles, explosive substances — UNLESS otherwise expressly stated in the policy.

7. **Missing/Mislaid Property:** Loss of Insured Property that is missing, mislaid, or whose disappearance cannot be linked to any single identifiable event.

8. **Property Removed:** Loss/damage to Insured Property removed from the Home to any other place.

9. **Consequential Loss:** Loss of earnings, loss by delay, loss of market, or other consequential or indirect loss of any kind or description.

10. **Reduction in Market Value:** Any reduction in market value of Insured Property after repair or reinstatement.

11. **Structural Additions > 10%:** Any addition, extension, or alteration to Home Building structure increasing Carpet Area by > 10% of Carpet Area at Commencement Date or renewal date — UNLESS additional premium paid and added by Endorsement.

12. **Claim Preparation Costs:** Costs, fees, or expenses for preparing any claim.
```

---

## Resource #10: `policy/10-claims-procedure.md`

**URI:** `policy://icici-lombard/home-protect/claims-procedure`
**Name:** Claims Procedure
**Description:** Step-by-step claims process, timelines, documentation requirements, fraudulent claim consequences, other insurance provisions, and recovery rights.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Claims Procedure (Clause G — Part IV)

## 1. Immediate Notice to Insurer
- Give immediate notice of loss/damage to any office or call centre
- Include: Policy number, name, police report details, authority report details, insured event details, brief loss statement, other insurance particulars, Optional Cover/Add-on details, photographs where possible

## 2. Steps to Prevent Further Loss
- Take all reasonable steps to prevent further loss
- Until insurer inspects and gives consent:
  - Do NOT sell, give away, or dispose of damaged items
  - Do NOT wash, clean, or remove damaged items/debris (except urgent necessity)
  - Do NOT carry out repairs (unless urgent and cannot contact insurer)

## 3. Immediate Notice to Authorities
- Fire/explosion/implosion/lightning → Fire brigade and police
- Subsidence/landslide/rockslide → District Administration
- Impact damage/riot/strikes/malicious damage/terrorism → Police
- Theft within 7 days of Insured Event → Police
- Condition may be waived for extreme hardship

## 4. Submit Claim
- Submit claim form within **30 days** from date of first noticing loss/damage
- Claim form available at branches and website
- Disclose any other insurance covering the same loss
- **12-month limitation:** No liability after 12 months from loss unless claim is subject of pending action/arbitration
- **12-month suit limitation:** If insurer disclaims liability, must file suit within 12 months from disclaimer date

## 5. Establish Loss
- Prove Insured Event occurred and extent of loss with full details
- Support with plans, specifications, vouchers, invoices for reconstruction/replacement/repairs
- Allow inspection, measurements, samples, photographs
- Give authority to access records and information from police/authorities
- For Personal Accident claims: Death Certificate and Post Mortem report

## 6. Fraudulent Claims
- If false/fraudulent claim or supporting documents:
  i. Insurer will not pay
  ii. Can cancel Policy (lose all benefits and premium)
  iii. Can inform police and start legal proceedings

## 7. Other Insurance
- Insured has right to claim under any covering policy
- If claimed under this policy: Settlement within this policy's limits and terms
- Insurer has right to seek contribution from other insurers
- Contribution clause NOT applicable if property is hypothecated to a bank/financial entity
- Insurer ensures its actions do not impose liability on insured

## 8. Recovery Action (Subrogation)
- After paying claim, insurer can pursue third party who caused loss
- Can act without insured's consent, in insured's name, regardless of full compensation
- Recovered amount: First to legal/recovery costs, then to claim amount, balance to insured
- Insured can only sue third party with insurer's prior consent
- Must not compromise/settle without insurer's consent
- If insured recovers from third party: Must return claim amount to insurer

## Claim Settlement Process
1. **Intimation:** Toll-free 1800 2666, email, or website
2. **FIR:** If applicable (third-party damage, fire, etc.)
3. **Surveyor:** Appointed within 24 hours of claim reporting
4. **Documents Required:**
   - Claim bill/form duly filled
   - Photographs/video of damaged property
   - Supporting documents (service engineer's report, fire brigade report, etc.)
   - FIR/final police investigation report
   - Repair/replacement quotations, invoices, payment proofs
   - Salvage offer (if applicable)
   - KYC/NEFT details
   - Invoice copy/GRN/damage certificate
   - Any other document specified by surveyor
5. **Assessment:** Approved by CSM/surveyor
6. **Salvage:** Assessed open market value of damaged asset (deducted from claim)
7. **TAT:** Survey report within 15 days of documents; claim decided within 7 days of survey report (exception for reinstatement value basis policies)
```

---

## Resource #11: `policy/11-policy-conditions.md`

**URI:** `policy://icici-lombard/home-protect/policy-conditions`
**Name:** Policyholder Obligations & Conditions
**Description:** Insured's duties including disclosure, care, change reporting, inspection cooperation.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Policyholder Obligations (Part III)

## 1. Full Disclosure
- Duty to disclose everything known (or reasonably expected to know) relevant to underwriting
- Even if not specifically asked
- Extends to information/declarations by anyone acting on behalf
- Insurance cover based entirely on information provided; conditional on truth of statements

## 2. Obligation to Take Care
- Keep Home Building and Contents in good condition and well maintained
- Ensure no visible/material structural faults that could aggravate loss
- Take care to prevent theft, loss, or damage
- Ensure unauthorized persons do not occupy Home Building

## 3. Inform Change in Circumstances (Immediately):
- Change of address
- Addition, alteration, extension to Home Building structure
- Letting out Home Building / no longer solely occupied
- Change of use of Home Building

## 4. Allow Inspection & Investigation
- Full cooperation with survey/investigation
- Allow inspection of interior, photographs, scientific testing
- Answer all questions truthfully and completely
- Submit all required documents

## 5. True Statements in Claims
- Give true and full information in claims; submit true documents
- False information/documents or withheld information → Right to refuse payment; may cancel policy
```

---

## Resource #12: `policy/12-renewal-cancellation-termination.md`

**URI:** `policy://icici-lombard/home-protect/renewal-cancellation-termination`
**Name:** Renewal, Cancellation & Termination Rules
**Description:** Policy end, renewal, cancellation by either party, and automatic termination triggers.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Renewal, Cancellation & Termination

## Renewal
- Policy expires at end of Policy Period
- Renewal is NOT automatic; insurer may seek relevant information
- Rejection only on grounds of: misrepresentation, non-disclosure, established fraud, or non-cooperation
- Must apply before Policy Period ends and pay required premium

## Cancellation by Insured
- Can cancel at any time by informing insurer

## Cancellation by Insurer
- Will NOT cancel during policy period
- Can cancel only for established fraud (minimum 7 days' notice)
- Refunds:
  - Policy ≤ 1 year with no claims: Proportionate premium for unexpired period
  - Policy > 1 year: Premium for unexpired policy years where coverage has not commenced

## Automatic Termination

### a. Destruction of Home Building
- Auto-ends 7 days after collapse/destruction by non-insured event
- For separable part/additional structure: Cover ends for that part only
- Can apply within 7 days to continue (insurer may agree but not obligated; same terms not guaranteed)

### b. Exhaustion of Sum Insured
- If full Sum Insured paid for any item (lost/destroyed/stolen/Total Loss): Cover for that item ends
- Can be reinstated if subject matter reconstructed and additional premium paid
- If total Sum Insured paid for any claim: Entire policy ends

### c. Change of Use
- Policy ends if:
  - Home Building use changed from personal residence to other purpose
  - Home Contents used for non-personal purpose

### d. Sale of Home Building or Contents
- Policy ends when insured sells, surrenders, or releases interest in Home Building and/or Home Contents
- Partial sale: Policy ends to that extent

### e. Effect of Death
- Home Building Cover and Home Contents Cover continue for legal representative(s) during Policy Period, subject to all terms and conditions
```

---

## Resource #13: `policy/13-terrorism-cover.md`

**URI:** `policy://icici-lombard/home-protect/terrorism-cover`
**Name:** Terrorism Damage Cover Endorsement
**Description:** Terrorism cover details, definition, exclusions, limits of indemnity, and deductibles.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Terrorism Damage Cover Endorsement (Material Damage Only)

## Definition of Act of Terrorism
An act or series of acts, including but not limited to use of force/violence and/or threat thereof, by any person or group(s) whether acting alone or on behalf of/in connection with any organization(s) or government(s), or unlawful associations recognized under Unlawful Activities (Prevention) Amendment Act, 2008, committed for political, religious, ideological, or similar purposes including intention to influence government and/or put public in fear.

## What Is Covered
- Physical loss or physical damage during policy period caused by act of terrorism
- Also covers: Loss/damage from action taken by government or Military Authority in suppressing/controlling/preventing terrorism consequences

## Military Authority
Armed forces, para military forces, police, or any other authority constituted by government for maintaining law and order.

## Government Compensation
If insured is eligible under any government compensation plan, this policy is **excess** of any recovery from such plan.

## Excluded Losses
1. Seizure or legal/illegal occupation
2. Voluntary abandonment; confiscation, commandeering, nationalisation, requisition, detention, embargo, quarantine
3. Contraband, illegal transportation/trade
4. Pollution/contaminant seepage/discharge
5. Chemical/biological emission/release/dispersal
6. Asbestos emission/release/dispersal
7. Fines, levies, duties, penalties, compensation imposed by courts/authorities
8. Electronic means (computer hacking, virus, electromagnetic weapons) — except losses from computer systems used in weapon launch/guidance/firing mechanisms
9. Vandals, malicious acts, protests, strikes, riots
10. Government enforcement of reconstruction/demolition laws
11. Any consequential loss (loss of use, delay, loss of markets/income, depreciation, increased working costs)
12. Cessation/fluctuation of utilities (water, gas, electricity, telecom)
13. Threat or hoax
14. Burglary, housebreaking, looting, theft during terrorism response
15. Mysterious disappearance or unexplained loss
16. Mould, mildew, fungus, spores
17. Total/partial cessation of work or process interruption

## Limit of Indemnity
- Maximum: Total Sum Insured in Schedule OR ₹20,000,000,000 per compound/location, whichever is LOWER
- Multiple locations (floater basis): Maximum aggregate = Total Sum Insured or ₹2,000 Crore, whichever is lower
- Multiple policies at same compound/location: Maximum aggregate payable by all insurers = ₹2,000 Crore (pro-rated by Sum Insured if actual loss exceeds)

## Deductible (Excess)
| Nature of Risk | Deductible | Minimum | Maximum |
|---|---|---|---|
| Shops | 1% of claim | ₹10,000 | ₹5,00,000 |
| Non-Industrial | 1% of claim | ₹25,000 | ₹10,00,000 |
| Industrial | 5% of claim | ₹1,00,000 | ₹25,00,000 |

## Mid-Term Cover
If terrorism cover added during policy currency: **15-day waiting period** (no claims for terrorism events in first 15 days)

## Cancellation
- No premium refund for cancelling terrorism cover separately during policy period
- Refund allowed only when basic policy is also cancelled
- Mid-term cancel/rewrite to align with accounting year: Pro-rata refund allowed
- Other cancellations: Pro-rata refund only
```

---

## Resource #14: `policy/14-personal-accident-details.md`

**URI:** `policy://icici-lombard/home-protect/personal-accident`
**Name:** Section 15 — Personal Accident (Detailed)
**Description:** Complete Table of Benefits, additional benefits, exclusions, medical benefit extension, and mandatory claim documents.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Section 15: Personal Accident — Detailed

## Table of Benefits (% of Capital Sum Insured)

| Benefit | % of CSI |
|---------|----------|
| 1. Death | 100% |
| 2a. Loss of sight (both eyes) | 100% |
| 2b. Loss of two limbs | 100% |
| 2c. Loss of one limb and one eye | 100% |
| 3a. Loss of a hand | 50% |
| 3b. Loss of a leg | 50% |
| 3c. Loss of sight of one eye | 50% |
| 4. Permanent Total and absolute disablement | 100% |
| **5. Loss of Toes** | |
| — All toes | 20% |
| — Great toe (both phalanges) | 5% |
| — Great toe (one phalanx) | 2% |
| — Other (if more than one toe lost, each) | 1% |
| **Loss of Hearing** | |
| — Both ears | 75% |
| — One ear | 30% |
| d. Four fingers and thumb of one hand | 40% |
| e. Four fingers | 35% |
| **f. Loss of Thumb** | |
| — Both phalanges | 25% |
| — One phalanx | 10% |
| **g. Loss of Index Finger** | |
| — Three phalanges | 10% |
| — Two phalanges | 8% |
| — One phalanx | 4% |
| **h. Loss of Middle Finger** | |
| — Three phalanges | 6% |
| — Two phalanges | 4% |
| — One phalanx | 2% |
| **i. Loss of Ring Finger** | |
| — Three phalanges | 5% |
| — Two phalanges | 4% |
| — One phalanx | 2% |
| **j. Loss of Little Finger** | |
| — Three phalanges | 4% |
| — Two phalanges | 3% |
| — One phalanx | 2% |
| **k. Loss of Metacarpus** | |
| — First or second (additional) | 3% |
| — Third, fourth, or fifth (additional) | 2% |
| r. Any other permanent partial disablement | % as assessed by Medical Practitioner |
| 6. Temporary Total Disablement (per week) | 1% or ₹25,000, whichever is lower |

## Additional Benefit Covers (IN ADDITION to Capital Sum Insured)

| Benefit | Amount |
|---------|--------|
| Transportation of dead body (India) | 2% of CSI or ₹2,500, whichever is lower |
| Loss/damage of clothing from Accident | ₹2,000 or actual, whichever is lower |
| Ambulance charges | ₹2,000 or actual, whichever is lower |
| Children's Education Grant (on death/PTD) | 10% of CSI |
| Loss of Job (due to disabling injury) | 25% of CSI |
| Rehabilitation and Modification Allowance (on PTD, Items 2-4) | Up to 10% of CSI or ₹50,000, whichever is lower |

## Exclusions (What Is Not Covered)
1. Compensation under more than one Table of Benefits item for same disablement period (higher compensation payable)
2. Any payment after Items 1, 2, or 4 claim admitted/payable
3. Multiple claims under benefits 3, 5 & 6 exceeding 100% of CSI in any one period
4. Death/injury from:
   a. Suicide/intentional self-injury
   b. Under influence of intoxicating liquor/drugs
   c. Drug addiction/alcoholism
   d. Aviation/ballooning (except as fare-paying passenger in licensed aircraft)
   e. Pregnancy/childbirth
   f. Insanity
   g. HIV/AIDS/Venereal disease
   h. Breach of law with criminal intent
   i. Regular armed force membership
   j. Professional sports team membership
   k. Ship's crew membership
   l. Death due to sickness/disease
   m. Police personnel
   n. Border security personnel

## Acts of Terrorism: COVERED under Personal Accident

## Medical Benefit Extension
- On additional premium payment
- Covers medical expenses for Accident where claim is admitted
- Up to **50% of compensation paid** or **20% of Sum Insured**, whichever is less
- Requires detailed medical expense documents

## Mandatory Claim Documents

### a) Death:
i. PA Claim Form with Company Stamp & Employer's covering letter
ii. Attested FIR
iii. Attested PM Report
iv. Attested Death Certificate
v. Attested Spot Panchnama (spot accidental death)
vi. Attested Inquest Panchnama (if applicable)
vii. Railway Police Panchnama + Station Master report (railway accident)
viii. State Electricity Board certificate (electrocution)
ix. FSL Report (snake bite/poisonous animal bite)

### b) Permanent Total Disablement:
i. PA Claim Form with Company Stamp & Employer's covering letter
ii. Attested FIR (if reported)
iii. Disability Certificate (from civil hospital/govt hospital medical officer/civil surgeon)
iv. X-rays and diagnostic reports
v. Employer's accident description letter
vi. Colour photograph showing disability
vii. Original medical bills/prescriptions (if medical benefits covered)

### c) Temporary Total Disablement:
i. PA Claim Form with Company Stamp & Employer's covering letter
ii. Medical Certificate (confirming injury, rest/unfit period, fitness certificate)
iii. Attested FIR (if reported)
iv. Leave certificate from employer
v. Original medical bills, discharge card, X-ray report
```

---

## Resource #15: `policy/15-grievance-ombudsman.md`

**URI:** `policy://icici-lombard/home-protect/grievance-ombudsman`
**Name:** Grievance Redressal & Insurance Ombudsman Details
**Description:** Grievance resolution channels and complete list of Insurance Ombudsman offices with territorial jurisdiction.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Grievance Redressal & Ombudsman

## Step 1: Contact ICICI Lombard
- Branch office
- Toll-free: 1800-2666
- Website: www.icicilombard.com → Customer Support → Grievance Redressal

## Step 2: IRDAI (If Unsatisfied)
- Bima Bharosa Portal: https://bimabharosa.irdai.gov.in/
- IRDAI Grievance Call Centre (IGCC): 1800 4254 732 / 155255

## Step 3: Insurance Ombudsman (Subject to Jurisdiction)

| # | Office | Jurisdiction |
|---|--------|-------------|
| 1 | Ahmedabad | Gujarat, Dadra & Nagar Haveli, Daman and Diu |
| 2 | Bengaluru | Karnataka |
| 3 | Bhopal | Madhya Pradesh, Chhattisgarh |
| 4 | Bhubaneswar | Odisha |
| 5 | Chandigarh | Punjab, Haryana (excl. Gurugram/Faridabad/Sonepat/Bahadurgarh), Himachal Pradesh, J&K, Ladakh, Chandigarh |
| 6 | Chennai | Tamil Nadu, Pondicherry Town, Karaikal |
| 7 | Delhi | Delhi, Haryana (Gurugram/Faridabad/Sonepat/Bahadurgarh) |
| 8 | Ernakulam | Kerala, Lakshadweep, Mahe |
| 9 | Guwahati | Assam, Meghalaya, Manipur, Mizoram, Arunachal Pradesh, Nagaland, Tripura |
| 10 | Hyderabad | Andhra Pradesh, Telangana, Yanam, Part of Puducherry |
| 11 | Jaipur | Rajasthan |
| 12 | Kolkata | West Bengal, Sikkim, Andaman & Nicobar Islands |
| 13 | Lucknow | Multiple UP districts (southern/eastern UP) |
| 14 | Mumbai | Goa, Mumbai Metropolitan Region (excl. Navi Mumbai & Thane) |
| 15 | Noida | Uttarakhand, Multiple UP districts (western UP) |
| 16 | Patna | Bihar, Jharkhand |
| 17 | Pune | Maharashtra, Navi Mumbai & Thane area (excl. Mumbai Metropolitan Region) |
| 18 | Thane | Navi Mumbai, Thane District, Raigad, Palghar, Mumbai wards M/East, M/West, N, S, T |
```

---

## Resource #16: `policy/16-premium-and-sum-insured.md`

**URI:** `policy://icici-lombard/home-protect/premium-sum-insured`
**Name:** Premium Computation & Sum Insured Structure
**Description:** Premium components, GSTIN details, sum insured structure across all sections.
**MimeType:** `text/markdown`

### Content Outline

```markdown
# Premium Computation & Sum Insured Structure

## Premium Components
- Premium excluding Terrorism
- Terrorism Premium
- Net Premium
- SGST
- CGST
- **Total Amount** (Net Premium + SGST + CGST)

## Stamp Duty
₹0.5 paid in cash, demand draft, or pay order

## Company GSTIN
- GSTIN: 27AAACI7904G1ZN
- Place of Business: 414, ICICI Lombard House, Veer Sawarkar Marg, Mumbai - Prabhadevi, Maharashtra 400025
- Category: General Insurance Business Services 997137

## Sum Insured Structure (Per Section)

### Section 1: ICICI Bharat Griha Raksha (Mandatory)
- 1(a) Home Buildings Cover
- 1(b) Home Contents Cover
  - (i) Valuable Content on Agreed Value Basis
  - (ii) Personal Accident
  - (iii) EMI Protector
  - (iv) Accidental Damage Cover
  - (v) Tenant's Liability Insurance
  - (vi) Landscaping Cost
  - (vii) Damage to Utility Systems
  - (viii) Incidental Costs
  - (ix) Inadvertent Omission
  - (x) Minor Acquisitions

### Section 2: Additional Living Expenses
- (i) Hotel Stay
- (ii) Brokerage for Alternate Accommodation

### Sections 3–18: Individual Sum Insured and Deductible per section

### Add-On Covers (Separate Sum Insured)
- Loss of Rent
- Rent for Alternative Accommodation
- Cover for Valuable Contents on Agreed Value Basis
- Personal Accident (Self + Spouse)

## Annexure I: Hypothecation Details
- Location of Risk, Occupancy, Bank name, Type of charge

## Annexure II: Property Details
- Location of Risk, Occupancy
- Carpet Area (sq. metres)
- Rate of Cost of Construction (₹/sq. metre) — residential and additional structures
- Per-category Sum Insured:
  - Building including fitting and fixture
  - Additional structure
  - Contents — Furniture, Fixtures and Fittings (home furnishing)
  - Contents — Electrical/Electronic
  - Contents — Other
  - Contents — Valuable Contents

## Agreed Bank Clause
- If property mortgaged/hypothecated to a Bank:
  - Insurer pays Bank the entire claim amount
  - Bank receives for its own demand and as agent for other interested persons
  - Payment to Bank discharges insurer's liability
  - Change of use or sale by insured does not prejudice Bank's interest
  - Bank's acts/omissions do not invalidate cover (but must notify changes and pay additional premium)
  - Insurer subrogated to Bank's rights after payment
```

---

## Reference Resource: `reference/coverage-limits-quick-ref.json`

**URI:** `policy://icici-lombard/home-protect/reference/limits`
**Name:** Coverage Limits Quick Reference
**Description:** JSON-structured quick reference of all monetary limits, sub-limits, deductibles, and time limits across the policy.

```json
{
  "home_building": {
    "sum_insured": "As per Schedule (Cost of Construction at Commencement Date)",
    "annual_escalation": "10% per annum (max 100% of original)",
    "architect_surveyor_fees": "Up to 5% of claim amount",
    "debris_removal": "Up to 2% of claim amount",
    "loss_of_rent_max_period": "3 years"
  },
  "home_contents": {
    "built_in_cover": "20% of Home Building Sum Insured, max ₹10,00,000",
    "valuable_contents_valuation_waiver": "Sum Insured up to ₹5,00,000 AND individual item ≤ ₹1,00,000"
  },
  "personal_accident_optional": {
    "amount_per_person": "₹5,00,000"
  },
  "emi_protector": {
    "max_liability": "₹50,00,000",
    "indemnity_period": "3 months",
    "max_per_month": "₹20,00,000",
    "trigger_threshold": "Repair/replacement time > 1 month"
  },
  "utility_systems": {
    "limit": "2.5% of total Sum Insured"
  },
  "inadvertent_omission": {
    "limit": "10% of total Sum Insured"
  },
  "additional_living_expenses": {
    "pet_boarding_per_month": "₹30,000",
    "immediate_expenses": "₹50,000",
    "hiring_household_goods_per_month": "₹50,000"
  },
  "hotel_stay": {
    "max_days": 15,
    "per_day_limit": "0.05% of Building Sum Insured, max ₹15,000/day"
  },
  "brokerage_alternate": {
    "max": "₹50,000 or one month rent, whichever less"
  },
  "section_4_garden": {
    "limit": "As per Schedule"
  },
  "section_7_cash_transit": {
    "time_window": "6 hours from withdrawal"
  },
  "section_8_valuables_valuation_threshold": "₹50,000 individual value",
  "section_8_fine_arts_valuation_threshold": "₹25,000 individual value",
  "section_8_fine_arts_total_loss_threshold": "Repair cost > 75% of agreed value",
  "section_9_personal_liability": {
    "scope": "Within home building only",
    "bicycle_liability": "₹30,000 all claims per Policy Period"
  },
  "section_11_break_in": {
    "jewellery_declaration_threshold": "₹1,00,000 individual value",
    "unoccupied_limit": "30 consecutive days",
    "students_contents": "25% of Home Contents value",
    "security_upgrade": "₹50,000",
    "precautionary_repairs": "₹1,00,000"
  },
  "section_13_documents": {
    "overseas_limit": "90 days per Policy Period"
  },
  "section_14_lock_key": {
    "lock_replacement": "₹25,000",
    "lockout": "₹5,000",
    "rental_car": "₹5,000"
  },
  "section_16_glass": {
    "excess_per_claim": "₹500",
    "contents_damage_from_breakage": "₹5,000"
  },
  "section_17_pet": {
    "burial_cremation": "₹5,000",
    "lost_advertising_reward": "25% of Sum Insured or ₹25,000 whichever less",
    "permanently_lost_days": 90,
    "lost_days_threshold": 5
  },
  "personal_accident_section_15": {
    "temporary_total_disablement_max_weeks": 104,
    "temporary_total_disablement_per_week": "1% of CSI or ₹25,000 whichever lower",
    "dead_body_transport": "2% of CSI or ₹2,500 whichever lower",
    "clothing_damage": "₹2,000",
    "ambulance": "₹2,000",
    "children_education_grant": "10% of CSI",
    "loss_of_job": "25% of CSI",
    "rehabilitation_modification": "10% of CSI or ₹50,000 whichever lower",
    "medical_benefit": "50% of compensation or 20% of SI whichever less"
  },
  "terrorism": {
    "max_per_compound": "₹20,000,000,000",
    "mid_term_waiting_period": "15 days"
  },
  "claim_timelines": {
    "notice_to_insurer": "Immediately",
    "claim_form_submission": "30 days from first notice of loss",
    "claim_limitation": "12 months from loss",
    "suit_after_disclaimer": "12 months from disclaimer",
    "theft_report_window": "Immediately (FIR)",
    "document_theft_police_report": "24 hours",
    "lock_key_intimation": "24 hours",
    "lock_key_police_report": "24 hours",
    "lock_key_documents_return": "3 days",
    "pet_theft_police_complaint": "5 days",
    "pa_claim_notice": "Immediately in writing",
    "pa_detailed_statement": "14 days"
  }
}
```

---

## Reference Resource: `reference/faq-common-scenarios.md`

**URI:** `policy://icici-lombard/home-protect/reference/faq`
**Name:** FAQ & Common Scenarios
**Description:** Frequently asked questions and common claim scenarios to help the LLM answer practical queries.

```markdown
# Frequently Asked Questions & Common Scenarios

## Q: Can I opt for only Home Contents Cover without Home Building Cover?
A: Yes. Under Section 1, you can opt for either 1(a) Home Buildings, or 1(b) Home Contents, or both together. At least one other section (2-18) must also be selected.

## Q: Is earthquake damage covered?
A: Yes. Earthquake, volcanic eruption, and other convulsions of nature are covered under Insured Event #4 (Clause B) with no specific exclusions.

## Q: Is flood damage covered?
A: Yes. Storm, Cyclone, Typhoon, Tempest, Hurricane, Tornado, Tsunami, Flood and Inundation are covered under Insured Event #5.

## Q: Is theft covered?
A: Theft is covered ONLY if it occurs within 7 days of and is proximately caused by another Insured Event (like flood, fire, etc.) — under base Section 1. For standalone theft/burglary, you need Section 10 (Theft Cover for Valuables) or Section 11 (Break-in Cover).

## Q: Is my jewellery covered?
A: Jewellery falls under "Valuable Contents." It is NOT covered under basic Home Contents Cover. You need to opt for: (a) Optional Cover for Valuable Contents on Agreed Value Basis (Clause E(1)(a)), or (b) Section 10: Theft Cover for Valuables, or (c) Section 11: Break-in Cover (covers jewellery against burglary only, not theft).

## Q: Can I use my home as an office and still be covered?
A: Generally no — the building must be used for residence. However, there are two exceptions: (1) If you're self-employed and use it both for residence and livelihood, or (2) If you temporarily shifted your office home due to lockdown/closure ordered by a public authority.

## Q: What happens if I sell my home?
A: The policy automatically terminates when you sell, surrender, or release your interest in the Home Building and/or Home Contents.

## Q: What if I die during the policy period?
A: The Home Building Cover and Home Contents Cover continue for your legal representative(s) for the remaining Policy Period.

## Q: Is there an underinsurance penalty?
A: No. The Complete Home Protect Policy has a Waiver of Underinsurance. If your Sum Insured is less than actual value at risk, it will NOT reduce your claim payment.

## Q: Does the Sum Insured automatically increase?
A: Yes, for policies with terms longer than 1 year. The Sum Insured increases by 10% per annum on each anniversary (max 100% of original), with no extra premium. For annual policies, there's a daily escalation of 1/365th of 10%.

## Q: What is the time limit to file a claim?
A: You must submit the claim form within 30 days of first noticing the loss. The insurer is not liable after 12 months from the loss unless the claim is in pending action/arbitration.

## Q: Is damage from a burst water pipe covered?
A: Yes. Bursting or overflowing of water tanks, apparatus, and pipes is Insured Event #12 with no specific exclusions under the base cover.

## Q: Are my electronics covered for electrical short circuit damage?
A: Under General Exclusion #5, electrical/electronic damage from short-circuiting, arcing, etc. is excluded — but only for the particular machine affected. If the short circuit causes a fire that damages other property, that fire damage IS covered. For the machine itself, you would need Section 3: Mechanical and/or Electrical Breakdown Cover.

## Q: Can Section 5 (All Risk) and Section 11 (Break-in) both be opted?
A: No. They are mutually exclusive. If you opt for one, you cannot opt for the other.

## Q: What pets are covered?
A: Only dogs and cats (Indigenous, Cross-bred, and Exotic breeds). The pet must be for companionship only, not income-earning. Coverage is under Section 17.

## Q: Is my bicycle covered?
A: Yes, under Section 12 (Recreational Bicycle Cover). This covers pedal cycles, tricycles, and manually driven scooters (not motor/electric). Does not cover competition/racing use.
```

---

## MCP Server Resource Registration (Spring Boot Code Snippet)

```java
@Service
public class PolicyResourceProvider {

    @Tool(description = "Retrieve ICICI Lombard Complete Home Protect Policy resource by topic")
    public String getPolicyResource(
        @ToolParam(description = "Resource topic: overview, definitions, insured-events, "
            + "home-building-cover, home-contents-cover, additional-covers-optional, "
            + "add-on-covers, section-wise-covers, general-exclusions, claims-procedure, "
            + "policy-conditions, renewal-cancellation-termination, terrorism-cover, "
            + "personal-accident, grievance-ombudsman, premium-sum-insured, "
            + "limits-quick-ref, faq") String topic
    ) {
        // Load and return the corresponding resource file
        String resourcePath = resolveResourcePath(topic);
        return loadResource(resourcePath);
    }

    private String resolveResourcePath(String topic) {
        return switch (topic) {
            case "overview" -> "static/policy/01-policy-overview.md";
            case "definitions" -> "static/policy/02-definitions.md";
            case "insured-events" -> "static/policy/03-insured-events.md";
            case "home-building-cover" -> "static/policy/04-home-building-cover.md";
            case "home-contents-cover" -> "static/policy/05-home-contents-cover.md";
            case "additional-covers-optional" -> "static/policy/06-additional-covers-optional.md";
            case "add-on-covers" -> "static/policy/07-add-on-covers.md";
            case "section-wise-covers" -> "static/policy/08-section-wise-covers.md";
            case "general-exclusions" -> "static/policy/09-general-exclusions.md";
            case "claims-procedure" -> "static/policy/10-claims-procedure.md";
            case "policy-conditions" -> "static/policy/11-policy-conditions.md";
            case "renewal-cancellation-termination" -> "static/policy/12-renewal-cancellation-termination.md";
            case "terrorism-cover" -> "static/policy/13-terrorism-cover.md";
            case "personal-accident" -> "static/policy/14-personal-accident-details.md";
            case "grievance-ombudsman" -> "static/policy/15-grievance-ombudsman.md";
            case "premium-sum-insured" -> "static/policy/16-premium-and-sum-insured.md";
            case "limits-quick-ref" -> "static/reference/coverage-limits-quick-ref.json";
            case "faq" -> "static/reference/faq-common-scenarios.md";
            default -> throw new IllegalArgumentException("Unknown topic: " + topic);
        };
    }
}
```

---

## Prompt Template for the Resources Section in MCP Server Prompt

Add the following to the **Resources** section of your MCP server design prompt:

```
Resources:-

1. **Policy Overview & Structure** (`01-policy-overview.md`)
   - Product identity (insurer, IRDAI reg, UIN, CIN)
   - Contact information (toll-free, email, website, addresses)
   - Three-part policy structure (Schedule, Wordings, T&C)
   - Complete section index (Sections 1–18)
   - Add-on covers index, optional covers, additional benefits
   - Insurance contract components
   - Waiver of Underinsurance principle

2. **Definitions & Key Terms** (`02-definitions.md`)
   - 30+ defined terms: Accident, Bank, Carpet Area, Commencement Date, Cost of Construction, Endorsement, Home Contents, General Contents, Valuable Contents, Insured, Insured Property, Kutcha/Pucca Construction, Market Value (with depreciation schedule), Policy Period, Premium, Salvage, Spouse, Sum Insured, Total Loss, Your Home Building, etc.
   - Section-specific definitions: Burglary, Theft, Hold Up, Lawn Belongings, Domestic Appliances, High Value Electronics, Pet, Family/Family Member, EMI, Financial Institution
   - Personal Accident definitions: Capital Sum Insured, Insured Person, In-Patient Care, Hospital/Nursing Home criteria, Medical Expenses, Medical Practitioner, Loss of Limbs, Physical Separation, Permanent/Temporary Total Disablement, Child, Cumulative Bonus, Qualified Nurse

3. **Insured Events — Clause B** (`03-insured-events.md`)
   - 14 insured events with covered/not-covered details:
     Fire, Explosion/Implosion, Lightning, Earthquake/Volcanic Eruption, Storm/Cyclone/Flood/Tsunami, Subsidence/Landslide/Rockslide, Bush/Forest/Jungle Fire, Impact Damage, Missile Testing, Riot/Strikes/Malicious Damages, Acts of Terrorism, Water Tank/Pipe Bursting, Sprinkler Leakage, Theft (within 7 days of insured event)

4. **Home Building Cover — Clause C** (`04-home-building-cover.md`)
   - Building definition (what is/isn't included, additional structures)
   - Residential use restrictions (exceptions for self-employment and lockdown)
   - Sum Insured rules (base, annual 10% escalation, daily escalation, restoration)
   - Payment calculation (partial damage, total loss, additional structures, architect/debris fees)
   - Loss of Rent & Alternative Accommodation (tenant provisions, calculation formula, 3-year max, architect certificate requirement)

5. **Home Contents Cover — Clause D** (`05-home-contents-cover.md`)
   - General Contents coverage, Valuable Contents exclusion
   - Built-in cover (20% of Building SI, max ₹10 Lakh)
   - Three settlement options (reimburse repairs, replace, or repair)
   - Sub-limits per item/category

6. **Optional & Additional Covers — Clause E** (`06-additional-covers-optional.md`)
   - Valuable Contents on Agreed Value Basis (valuation certificate rules, ₹5L/₹1L thresholds, pair & set rule)
   - Personal Accident Cover (₹5,00,000 per person, continuation for spouse)

7. **Add-On Covers** (`07-add-on-covers.md`)
   - EMI Protector (trigger conditions, 3-month indemnity, ₹50L limit, ₹20L/month max)
   - Accidental Damage Cover (9 exclusion categories)
   - Tenant's Liability Insurance (sub-let exclusion, initial indemnity rule)
   - Landscaping Cost
   - Damage to Utility Systems (2.5% of total SI limit, electricity/gas/AC only)
   - Incidental Costs (percentage of admissible claim)
   - Inadvertent Omission (10% of total SI)
   - Minor Acquisitions (3-month notification, exclusion list)

8. **Sections 2–18 Coverage Details** (`08-section-wise-covers.md`)
   - Section 2: Additional Living Expenses (pet boarding ₹30K/mo, immediate expenses ₹50K, household goods hiring ₹50K/mo, temporary resettlement) + Hotel Stay (15 days, ₹15K/day) + Brokerage (₹50K or 1 month rent)
   - Section 3: Mechanical/Electrical Breakdown (appliances, high-value electronics, 10-year age limit, Market Value basis)
   - Section 4: Garden Cover (lawn belongings, trees, shrubs; excludes animals/insects/natural decay)
   - Section 5: Home Contents All Risk (mutually exclusive with Section 11)
   - Section 6: Loss of Contents in Transit (3 transits, 30 days, prior notice, packing exclusion)
   - Section 7: Loss of Cash in Transit (6-hour window from bank/ATM, FIR required)
   - Section 8: Specified Articles (baggage outside home, Valuables/Fine Arts valuation thresholds, Pair & Set rule)
   - Section 9: Personal Liability (within home building, third-party death/injury/property, domestic staff, intoxication exclusion, India jurisdiction only)
   - Section 10: Theft Cover for Valuables
   - Section 11: Break-in Cover (burglary definition, jewellery ₹1L declaration threshold, 30-day unoccupied limit, Students Contents 25%, Security Upgrade ₹50K, Precautionary Repairs ₹1L)
   - Section 12: Recreational Bicycle Cover (manual bikes only, ₹30K third-party liability)
   - Section 13: Loss of Documents (passport, DL, title deeds; 90-day overseas; 24-hour police report)
   - Section 14: Lock & Key Replacement (₹25K locks, ₹5K lockout, ₹5K rental car)
   - Section 16: Fixed Glass & Sanitary Fittings (₹500 excess, ₹5K contents damage)
   - Section 17: Pet Cover (dogs & cats only; vet expenses, death, theft, third-party liability; ₹5K burial; 5-day/90-day stolen thresholds)
   - Section 18: Permanent Relocation Cover (Ready Reckoner or Registration Value basis)

9. **General Exclusions — Clause F** (`09-general-exclusions.md`)
   - 12 universal exclusions: deliberate acts, war, nuclear, pollution (with exceptions), electrical damage (per-machine), bullion/documents, missing property, removed property, consequential loss, market value reduction, structural additions >10%, claim preparation costs

10. **Claims Procedure** (`10-claims-procedure.md`)
    - 8-step process: immediate notice, loss prevention steps, authority notification, claim submission (30-day form deadline, 12-month limitation), loss establishment, fraudulent claim consequences, other insurance/contribution, subrogation/recovery rights
    - Settlement process: intimation → FIR → surveyor (24hr) → documents → assessment → salvage → TAT (15 days survey + 7 days decision)

11. **Policyholder Obligations** (`11-policy-conditions.md`)
    - Disclosure duty, care obligations, change notification, inspection cooperation, true statements

12. **Renewal, Cancellation & Termination** (`12-renewal-cancellation-termination.md`)
    - Non-automatic renewal (rejection grounds), cancellation by either party, 5 automatic termination triggers (destruction, SI exhaustion, use change, sale, death)

13. **Terrorism Cover Endorsement** (`13-terrorism-cover.md`)
    - Terrorism definition, 17 excluded losses, ₹2,000 Crore per-compound limit, deductible table, 15-day mid-term waiting period, cancellation rules

14. **Personal Accident Details — Section 15** (`14-personal-accident-details.md`)
    - Complete Table of Benefits (death 100%, limb/sight losses, finger/toe/hearing percentages, Temporary Total Disablement 1%/week)
    - 6 additional benefit covers (transport, clothing, ambulance, education grant, job loss, rehabilitation)
    - 14 exclusion categories, Acts of Terrorism covered
    - Medical Benefit extension, mandatory claim documents for death/PTD/TTD

15. **Grievance & Ombudsman** (`15-grievance-ombudsman.md`)
    - 3-step escalation: ICICI Lombard → IRDAI Bima Bharosa → Insurance Ombudsman
    - 18 Ombudsman offices with territorial jurisdiction mapping

16. **Premium & Sum Insured Structure** (`16-premium-and-sum-insured.md`)
    - Premium components (base, terrorism, GST), stamp duty, GSTIN
    - Sum Insured structure per section, Annexure I (hypothecation), Annexure II (property details)
    - Agreed Bank Clause (7 provisions for mortgaged properties)

17. **Coverage Limits Quick Reference** (`coverage-limits-quick-ref.json`)
    - All monetary limits, sub-limits, deductibles, time limits in structured JSON format for fast LLM lookup

18. **FAQ & Common Scenarios** (`faq-common-scenarios.md`)
    - 15+ practical Q&A covering: section selection rules, earthquake/flood coverage, theft vs burglary, jewellery coverage options, home-as-office rules, sale/death scenarios, underinsurance waiver, SI escalation, claim timelines, pipe burst, short circuit, Section 5 vs 11 exclusivity, pets, bicycles
```
