# Design System: Azure Fitness Dashboard
**Project ID:** Reference-only local mockup  
**Reference assets:** `C:\Users\omkar\Downloads\bloom.png`, `D:\bloom\.raw\color-blue.jpg`

## 1. Visual Theme & Atmosphere
The interface should feel like a premium mid-morning fitness dashboard lit by clear, cool daylight. The dominant mood is calm, focused, and refreshing: a light periwinkle-blue app with deep steel-blue activity signals, sky-blue highlights, glassy rounded cards, and soft airy depth.

The design is dense but breathable. It shows many health metrics on one mobile screen, yet the hierarchy stays clear because content is grouped into large rounded modules with generous internal padding. The background carries a luminous light-to-mid blue gradient, while the actual content surfaces stay bright and restrained with subtle blue tinting.

Use a clean, high-end mobile-app treatment rather than a heavy dark dashboard. Panels should feel lifted and airy through subtle borders, soft shadows, and light-contrast blue gradients. Accent colors should feel crisp and precise: deep steel blue (`#395886`) for activity, progress, alerts, selected navigation, and chart lines; cornflower blue (`#8AAEE0`) for positive emphasis, selected dates, active ring caps, and motivational copy. The lightest blues (`#F0F3FA`, `#D5DEEF`) should dominate surfaces, keeping the overall feel open and fresh.

Avoid a stark white clinical aesthetic. This system is cool-toned, softly saturated, sporty, and airy. Lighter blues are the primary visual language; darker blues appear as deliberate accents.

## 2. Color Palette & Roles
* **Cloud White (`#F0F3FA`)**: Lightest tone from the color reference. Use as the primary app background, the base of all card surfaces, unselected date chips, and the default screen fill. This shade should dominate the overall surface area.
* **Periwinkle Mist (`#D5DEEF`)**: Second lightest tone. Use for card fills, secondary surfaces, date-chip backgrounds, stat module backgrounds, and subtle dividers. Prefer this over white for cards to keep the palette cohesive.
* **Sky Steel (`#B1C9EF`)**: Mid-light blue. Use for selected state backgrounds, inner ring tracks, chart area fills, inactive progress tracks, soft highlight glows behind icons, and card gradient stops. This is the go-to for anything that needs emphasis without being heavy.
* **Cornflower Blue (`#8AAEE0`)**: Medium blue. Use for motivational emphasis text, selected chart target markers, progress ring caps, secondary active states, and warm interactive highlights. Think of this as the "positive action" color — energetic but not aggressive.
* **Steel Blue (`#638ECB`)**: Medium-dark blue. Use for section icons, secondary active labels, chart point markers, supporting progress arcs, and supporting navigation icon strokes. Bridges the light surfaces and the deep accent.
* **Deep Navy (`#395886`)**: Darkest tone from the color reference. Use sparingly for primary text on light surfaces, active navigation labels, primary progress rings, chart lines, alert dots, selected labels, and the bottom navigation bar. This is the high-contrast anchor of the palette.

### Gradients
* **Screen Bloom Gradient:** vertical atmospheric gradient from Periwinkle Mist (`#D5DEEF`) at the top, through Sky Steel (`#B1C9EF`) and Cornflower Blue (`#8AAEE0`) in the upper-middle glow, settling into Cloud White (`#F0F3FA`) across the lower two-thirds of the screen.
* **Card Gradient:** subtle diagonal or vertical blend from Cloud White (`#F0F3FA`) to Periwinkle Mist (`#D5DEEF`). Keep contrast very low; the card should read as one bright surface with gentle dimensionality.
* **Deep Progress Gradient:** Deep Navy (`#395886`) to Steel Blue (`#638ECB`) for primary active progress rings and chart lines. Use this for calorie burns, distance, and high-priority metrics.
* **Sky Progress Gradient:** Cornflower Blue (`#8AAEE0`) to Sky Steel (`#B1C9EF`) for steps, date selection, and lighter positive status indicators.

## 3. Typography Rules
Use a modern geometric sans serif with excellent numeric rendering. Preferred stack: `Inter`, `SF Pro Display`, `Roboto`, system sans-serif. The UI should look native to a high-end mobile fitness app, so avoid decorative fonts.

Headlines use heavy weights and tight but not compressed spacing. The main workout title should be large, bold, and deep navy, with a confident two-line break when needed. Motivational emphasis text uses Cornflower Blue (`#8AAEE0`) and a bold weight to feel energetic and fresh.

Numbers are a major visual element. Use strong weights for dates, calories, steps, time, and weight values. Keep units smaller and lighter than the number so the metric remains scannable. Example: `1,690` should be bold and Deep Navy; `kcal burned` should sit below it in a lighter Steel Blue weight.

Body labels and card titles should be medium weight with generous line height. Inactive navigation text should be Steel Blue (`#638ECB`). Do not use negative letter spacing. Maintain natural spacing so the design feels polished rather than squeezed.

Recommended type scale for a 941 by 1672 portrait mobile canvas:
* **Status time:** 26-30 px, medium weight.
* **Greeting:** 34-38 px, regular weight, Deep Navy.
* **Motivational line:** 38-44 px, bold weight, Cornflower Blue.
* **Hero title:** 54-64 px, heavy weight, Deep Navy.
* **Card title:** 30-36 px, regular or medium weight, Steel Blue.
* **Primary metrics:** 42-52 px, bold weight, Deep Navy.
* **Secondary stats:** 25-31 px, regular weight, Steel Blue.
* **Bottom navigation labels:** 23-27 px, regular weight.

## 4. Component Stylings
* **App Background:** Full-screen light gradient with a cool blue bloom concentrated behind the header and date row. The lower 60 percent should settle into Cloud White (`#F0F3FA`) so cards float cleanly above it. Add soft blur-like transitions rather than hard bands.
* **Status Bar:** Deep Navy icons and time. Keep it visually simple, floating directly over the background with no container.
* **Profile Button:** Circular Periwinkle Mist (`#D5DEEF`) button, roughly 118 px in diameter on the reference canvas. Use a Steel Blue avatar glyph inside. No label. Add a soft sky-blue shadow and a subtle rim so it sits above the background.
* **Notification Button:** Circular Periwinkle Mist button matching the profile button. Use a Deep Navy line icon and a small Steel Blue (`#638ECB`) notification dot at the top-right edge. The badge should overlap the circle slightly.
* **Greeting Block:** Left-aligned text. The first line is Deep Navy and regular weight; the second line is Cornflower Blue and bold. Keep the greeting visually connected to the avatar, not centered.
* **Date Chips:** Pill-shaped vertical capsules with Periwinkle Mist (`#D5DEEF`) fill, subtle sky shadow, and circular inner outlines in Sky Steel (`#B1C9EF`). Unselected chips use Steel Blue numbers and text. The selected chip becomes a Cornflower Blue (`#8AAEE0`) pill with Deep Navy text. Selected state should communicate through color and soft glow, not layout shift.
* **Hero Plan Card:** Large rounded rectangle with generous corners, Cloud White to Periwinkle Mist gradient fill, and a Sky Steel (`#B1C9EF`) border. It contains a Steel Blue calendar icon, a small title, a large Deep Navy workout title, a Steel Blue clock icon, and a duration. Right side supports a mascot or workout illustration. The illustration should sit inside the card without a visible frame and cast a small soft shadow.
* **Metric Cards:** Two-column grid cards below the hero. Cards use Cloud White surface with Periwinkle Mist border treatment. Each card has a Steel Blue line icon at top-left, a title in Steel Blue, and a large circular or semi-circular progress visualization centered in the body.
* **Progress Rings:** Use thick strokes with rounded caps. Calorie ring is almost closed and Deep Navy-dominant. Step ring is a top arc, Cornflower Blue-dominant, with an inactive Sky Steel track. Ring interiors remain transparent/light, allowing central metrics to breathe.
* **Chart Card:** Full-width card with Deep Navy line chart, glowing Cornflower Blue point markers, muted Sky Steel grid lines, and a dashed horizontal target line in Periwinkle Mist. Use a right-side target annotation rather than a legend box. Bottom summary stats are divided into three zones: Start, Current, Target.
* **Bottom Navigation:** Large rounded Periwinkle Mist pill container docked near the bottom with four evenly spaced items. Active item uses Deep Navy (`#395886`) for icon and label. Inactive items use Steel Blue (`#638ECB`) and thin line icons. The nav container uses a subtle Sky Steel border and soft shadow, not a heavy fill.
* **Buttons:** Prefer circular icon buttons or pill-shaped controls. Use icon-first actions. Primary selected states should be Deep Navy or Cornflower Blue depending on meaning: deep navy for action/activity, cornflower blue for current selection or warmth.
* **Inputs/Forms:** If forms are introduced, use Cloud White filled fields with Sky Steel (`#B1C9EF`) border, Deep Navy entered text, Steel Blue placeholders, and Cornflower Blue focus rings. Corners should be generously rounded, matching the card language.

## 5. Layout Principles
Design for a tall mobile portrait screen first. The reference composition is approximately 941 by 1672, with comfortable edge margins around 30-34 px. Major sections are stacked vertically and aligned to a consistent left and right edge.

The vertical rhythm is compact but never cramped. Header, date row, hero, metric grid, chart, and navigation should feel like one continuous dashboard. Keep inter-section gaps around 18-28 px on the reference canvas; internal card padding should be larger, usually 32-48 px for major cards.

Use a two-column grid only for peer metrics. All narrative or summary content should span full width. The hero card and report chart should be full-width anchors that stabilize the screen.

Maintain strong hierarchy:
* Header establishes personality and daily context.
* Date chips establish current day.
* Hero card gives the primary workout action.
* Metric cards show immediate fitness status.
* Chart card shows longer-term reporting.
* Bottom nav provides persistent app structure.

Icons should be outline-based, simple, and energetic. Steel Blue icons mark active dashboard sections. Inactive icons should use a lighter Sky Steel and never compete with the metrics.

## 6. Shape, Radius & Geometry
The system uses soft, sporty geometry. Avoid sharp rectangles.

* **Large cards:** generously rounded corners, approximately 36-44 px radius on the reference canvas.
* **Metric cards:** same rounded language, slightly smaller visual mass but still generous.
* **Date chips:** vertical pills with fully rounded ends.
* **Avatar and notification controls:** perfect circles.
* **Bottom nav:** long pill-like rounded rectangle with large corner radius.
* **Progress rings:** rounded stroke caps only; no squared-off ring ends.

Card borders are visible but quiet. Use 1-2 px Sky Steel (`#B1C9EF`) with reduced opacity when possible. Do not use hard outlines.

## 7. Depth, Shadows & Effects
Depth is soft and airy. Use layered shadows, low-contrast gradients, and glow-like color transitions rather than hard drop shadows.

Cards should feel slightly lifted from the light background. Suggested treatment: Cloud White or Periwinkle Mist fill, 1 px Sky Steel border, faint inner highlight along the top-left edge in near-white, and a soft Periwinkle Mist shadow below. Circular controls can use stronger shadows because they float over the gradient header area.

Cornflower Blue chart points and active indicators may glow subtly. Keep glows tight and purposeful; they should support activity feedback, not decorate every element.

The background gradient supplies most of the atmosphere. Do not add extra decorative orbs, bokeh blobs, or unrelated gradients inside content cards.

## 8. Data Visualization Rules
Use Deep Navy and Cornflower Blue for meaningful activity progress. Never introduce random chart colors. The dashboard should feel like one cohesive athletic instrument panel.

Progress visualizations should be thick, rounded, and readable at a glance. Inactive tracks use Sky Steel (`#B1C9EF`) at low opacity. Active progress uses either the Deep Progress Gradient or the Sky Progress Gradient.

Line charts use:
* Deep Navy solid line for the primary trend.
* Circular point markers with Cornflower Blue centers or highlights.
* Muted Sky Steel horizontal grid lines.
* Dashed target line in Periwinkle Mist or dim blue-white.
* Target annotations in Deep Navy and Steel Blue, with Cornflower Blue emphasis for key values.

Use color semantically:
* Deep Navy means active burn, current value, urgency, selected navigation, or primary action.
* Cornflower Blue means selected day, target highlight, positive warmth, or celebratory emphasis.
* Sky Steel and Periwinkle Mist mean structure, inactive states, and background calm.
* Cloud White means open space, card surfaces, and breathing room.

## 9. Illustration & Imagery
Mascot or character art should be stylized, compact, and integrated into the card layout. It can use Steel Blue and Deep Navy accents with light blue highlights so it belongs to the palette. The illustration should not overpower the workout text; its role is to add personality to the primary plan card.

If new images are generated for this system, describe the style as: cool blue character accents, bold deep navy outlines, sky-blue highlights, and a grounded soft shadow. Avoid warm-toned or red-dominant artwork. Avoid photorealistic stock fitness imagery unless the entire screen direction changes.

## 10. Accessibility & Contrast
Primary text in Deep Navy (`#395886`) on Cloud White (`#F0F3FA`) or Periwinkle Mist (`#D5DEEF`) surfaces passes WCAG AA at normal text sizes. Avoid placing small text directly on Cornflower Blue or Sky Steel without a white backing card. If text must sit over a medium blue, use Deep Navy text only on solid light-blue selected controls or add a Cloud White surface behind it.

Deep Navy on light blue is acceptable for icons and active labels, but avoid it for long paragraphs. Cornflower Blue on Cloud White has good readability for short emphasis text. Steel Blue labels on Periwinkle Mist should remain secondary and visibly quieter than Deep Navy primary labels.

Touch targets should be large and thumb-friendly. Circular controls, date chips, and nav items should all remain at least 44 px in real device CSS pixels after scaling.

## 11. Prompting Guidance For New Screens
When asking an image or UI generator to create additional screens, describe the design as:

"A premium light mobile fitness dashboard with a soft azure-blue gradient header fading into a near-white background, glassy rounded periwinkle-blue cards, deep navy activity accents, cornflower-blue selected states, bold deep-navy metric typography, pill-shaped date controls, thick rounded progress rings, and outline fitness icons. Keep the layout airy, dense, and polished with soft shadows and subtle sky-blue borders."

Preserve these constants across new screens:
* Light periwinkle-to-cloud-white app base with cool blue top glow.
* Large rounded card containers in Cloud White or Periwinkle Mist.
* Deep Navy for active movement and primary actions.
* Cornflower Blue for selected or encouraging states.
* Deep Navy bold numerals as the primary data language.
* Subtle Sky Steel borders and soft airy depth.
* Lighter shades (`#F0F3FA`, `#D5DEEF`, `#B1C9EF`) should dominate overall surface area; darker shades (`#638ECB`, `#395886`) are reserved for accents and high-contrast moments.
