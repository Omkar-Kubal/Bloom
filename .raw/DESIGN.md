# Design System: Ember Fitness Dashboard
**Project ID:** Reference-only local mockup  
**Reference assets:** `C:\Users\omkar\Downloads\bloom.png`, `C:\Users\omkar\Downloads\color-red.jpg`

## 1. Visual Theme & Atmosphere
The interface should feel like a premium late-evening fitness dashboard lit by warm ember light. The dominant mood is energetic, focused, and slightly playful: a dark navy workout app with red-orange activity signals, peach highlights, glassy rounded cards, and soft atmospheric depth.

The design is dense but calm. It shows many health metrics on one mobile screen, yet the hierarchy stays clear because content is grouped into large rounded modules with generous internal padding. The background carries a dramatic red-to-peach-to-navy bloom, while the actual content surfaces remain dark and restrained.

Use a cinematic mobile-app treatment rather than a flat utility dashboard. Panels should feel inset into the screen through subtle borders, soft shadows, and low-contrast navy gradients. Accent colors should feel hot and active: red for activity, progress, alerts, selected navigation, and chart lines; peach for positive emphasis, selected dates, active ring caps, and motivational copy.

Avoid a clean white wellness aesthetic. This system is dark, saturated, sporty, and atmospheric.

## 2. Color Palette & Roles
* **Abyss Navy (`#171C2F`)**: Primary dark surface color from the color reference. Use for the deepest cards, circular icon buttons, unselected date chips, bottom navigation, and dark UI shells.
* **Deep Ink Background (`#001630`)**: Near-black blue sampled from the main mockup. Use for the app base, especially below the warm header bloom.
* **Panel Navy (`#0E1828`)**: Main card fill. Use for large dashboard cards, stat modules, and chart containers.
* **Raised Midnight (`#142338`)**: Secondary dark surface for layered panels and card gradients. Use when a component needs to separate from the base without becoming visibly blue.
* **Muted Steel Navy (`#232F49`)**: Secondary surface tone from the color reference. Use for inactive UI states, subdued controls, and depth layers behind primary panels.
* **Blue Slate Border (`#384358`)**: Structural outline color from the color reference. Use for card strokes, dividers, date-chip outlines, chart rules, and inactive icon strokes.
* **Bloom Peach (`#FFA586`)**: Warm highlight from the color reference. Use for the selected date pill, motivational emphasis text, selected chart target markers, progress ring caps, and soft highlight glows.
* **Soft Peach Sample (`#F49E85`)**: Slightly muted peach sampled from the reference file. Use for gradients, anti-aliased ring edges, and secondary warm emphasis where full `#FFA586` feels too bright.
* **Activity Red (`#E51A2B`)**: Core action and progress color from the color reference. Use for active navigation, primary progress rings, chart lines, section icons, alert dots, and selected labels.
* **Hot Coral Red (`#FF3A3D`)**: Brighter UI red seen in the mockup. Use sparingly for icons, notification badges, graph points, and high-visibility active strokes.
* **Wine Shadow (`#561A22`)**: Deep red-brown from the color reference. Use for dark gradient stops, deep background warmth, disabled red states, and shadowed illustration accents.
* **Header Burgundy (`#881C2C`)**: Dark warm background tone sampled from the color reference. Use in the upper background gradient behind the greeting area.
* **Text White (`#FCFBFB`)**: Primary text. Use for major headings, large metrics, status-bar icons, and high-priority labels.
* **Mist Text (`#DDE3EE`)**: Secondary readable text. Use for card titles, supporting labels, units, and descriptive stats.
* **Dim Steel Text (`#9AA4B6`)**: Inactive navigation labels, muted captions, helper text, and non-selected icon labels.

### Gradients
* **Screen Bloom Gradient:** vertical atmospheric gradient from Header Burgundy (`#881C2C`) at the top, through Activity Red (`#E51A2B`) and Bloom Peach (`#FFA586`) in the upper-middle glow, into Deep Ink Background (`#001630`) and Abyss Navy (`#171C2F`) at the bottom.
* **Card Gradient:** subtle diagonal or vertical blend from Panel Navy (`#0E1828`) to Raised Midnight (`#142338`). Keep contrast low; the card should read as one dark surface with gentle dimensionality.
* **Red Progress Gradient:** Activity Red (`#E51A2B`) to Hot Coral Red (`#FF3A3D`) with occasional Wine Shadow (`#561A22`) on inactive or shadowed segments.
* **Peach Progress Gradient:** Bloom Peach (`#FFA586`) to Soft Peach Sample (`#F49E85`) for steps, date selection, and warm positive status.

## 3. Typography Rules
Use a modern geometric sans serif with excellent numeric rendering. Preferred stack: `Inter`, `SF Pro Display`, `Roboto`, system sans-serif. The UI should look native to a high-end mobile fitness app, so avoid decorative fonts.

Headlines use heavy weights and tight but not compressed spacing. The main workout title should be large, bold, and white, with a confident two-line break when needed. Motivational emphasis text uses the peach accent and a bold weight to make it feel warm and personal.

Numbers are a major visual element. Use strong weights for dates, calories, steps, time, and weight values. Keep units smaller and lighter than the number so the metric remains scannable. Example: `1,690` should be bold and dominant; `kcal burned` should sit below it in a lighter weight.

Body labels and card titles should be medium weight with generous line height. Inactive navigation text should be visibly dimmer and lighter. Do not use negative letter spacing. Maintain natural spacing so the design feels polished rather than squeezed.

Recommended type scale for a 941 by 1672 portrait mobile canvas:
* **Status time:** 26-30 px, medium weight.
* **Greeting:** 34-38 px, regular weight.
* **Motivational line:** 38-44 px, bold weight, peach.
* **Hero title:** 54-64 px, heavy weight.
* **Card title:** 30-36 px, regular or medium weight.
* **Primary metrics:** 42-52 px, bold weight.
* **Secondary stats:** 25-31 px, regular weight.
* **Bottom navigation labels:** 23-27 px, regular weight.

## 4. Component Stylings
* **App Background:** Full-screen dark gradient with a warm bloom concentrated behind the header and date row. The lower 60 percent should settle into deep navy so cards have strong contrast. Add soft blur-like transitions rather than hard bands.
* **Status Bar:** White icons and time. Keep it visually simple, floating directly over the background with no container.
* **Profile Button:** Circular dark navy button, roughly 118 px in diameter on the reference canvas. Use a simple peach-red avatar glyph inside. No label. Add a soft dark shadow and a subtle rim so it sits above the background.
* **Notification Button:** Circular dark navy button matching the profile button. Use a white line icon and a small Hot Coral Red notification dot at the top-right edge. The badge should overlap the circle slightly.
* **Greeting Block:** Left-aligned text. The first line is white and lighter; the second line is peach and bold. Keep the greeting visually connected to the avatar, not centered.
* **Date Chips:** Pill-shaped vertical capsules with dark navy fill, subtle shadow, and circular inner outlines. Unselected chips use white numbers and text with a peach-red inner ring. The selected chip becomes a warm peach pill with black or very dark text. Selected state should be taller/brighter only through color and glow, not through disruptive layout shift.
* **Hero Plan Card:** Large rounded rectangle with generous corners, dark navy gradient fill, and Blue Slate Border (`#384358`). It contains a red calendar icon, a small title, a large workout title, a red clock icon, and a duration. Right side supports a mascot or workout illustration. The illustration should sit inside the card without a visible frame and should cast a small soft shadow.
* **Metric Cards:** Two-column grid cards below the hero. Cards use the same dark navy surface and border treatment. Each card has a red line icon at top-left, a title, and a large circular or semi-circular progress visualization centered in the body.
* **Progress Rings:** Use thick strokes with rounded caps. Calorie ring is almost closed and red-dominant. Step ring is a top arc, peach-dominant, with an inactive muted navy track. Ring interiors remain transparent/dark, allowing central metrics to breathe.
* **Chart Card:** Full-width card with red line chart, glowing point markers, muted grid lines, and a dashed horizontal target line. Use a right-side target annotation rather than a legend box. Bottom summary stats are divided into three zones: Start, Current, Target.
* **Bottom Navigation:** Large rounded dark pill container docked near the bottom with four evenly spaced items. Active item uses Hot Coral Red for icon and label. Inactive items use Dim Steel Text (`#9AA4B6`) and thin line icons. The nav container uses a subtle border and inner shadow, not a bright fill.
* **Buttons:** Prefer circular icon buttons or pill-shaped controls. Use icon-first actions. Primary selected states should be red or peach depending on meaning: red for action/activity, peach for current selection or warmth.
* **Inputs/Forms:** If forms are introduced, use dark filled fields with Blue Slate Border (`#384358`), white entered text, Dim Steel Text placeholders, and red focus rings. Corners should be generously rounded, matching the card language.

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

Icons should be outline-based, simple, and energetic. Red icons mark active dashboard sections. Inactive icons should be visibly quieter and should never compete with the metrics.

## 6. Shape, Radius & Geometry
The system uses soft, sporty geometry. Avoid sharp rectangles.

* **Large cards:** generously rounded corners, approximately 36-44 px radius on the reference canvas.
* **Metric cards:** same rounded language, slightly smaller visual mass but still generous.
* **Date chips:** vertical pills with fully rounded ends.
* **Avatar and notification controls:** perfect circles.
* **Bottom nav:** long pill-like rounded rectangle with large corner radius.
* **Progress rings:** rounded stroke caps only; no squared-off ring ends.

Card borders are visible but quiet. Use 1-2 px Blue Slate Border (`#384358`) with reduced opacity when possible. Do not use hard black outlines.

## 7. Depth, Shadows & Effects
Depth is soft and atmospheric. Use layered shadows, low-contrast gradients, and glow-like color transitions rather than hard drop shadows.

Cards should feel slightly raised from the dark background. Suggested treatment: dark navy fill, 1 px slate border, faint inner highlight along the top-left edge, and a soft shadow below. Circular controls can use stronger shadows because they float over the warm header bloom.

Red chart points and active indicators may glow subtly. Keep glows tight and purposeful; they should support activity feedback, not decorate every element.

The background itself supplies most of the drama. Do not add extra decorative orbs, bokeh blobs, or unrelated gradients inside content cards.

## 8. Data Visualization Rules
Use red and peach for meaningful activity progress. Never introduce random chart colors. The dashboard should feel like one athletic instrument panel.

Progress visualizations should be thick, rounded, and readable at a glance. Inactive tracks use muted navy or Wine Shadow at low opacity. Active progress uses either red or peach gradients.

Line charts use:
* Red solid line for the primary trend.
* Circular point markers with pale centers or highlights.
* Muted horizontal grid lines.
* Dashed target line in Mist Text or dim white.
* Target annotations in white and Mist Text, with peach/red emphasis for key values.

Use color semantically:
* Red means active burn, current value, urgency, selected navigation, or movement.
* Peach means selected day, target highlight, positive warmth, or celebratory emphasis.
* Navy means structure, inactive states, and background calm.

## 9. Illustration & Imagery
Mascot or character art should be stylized, compact, and integrated into the card layout. It can use deep reds, dark outlines, and warm highlights so it belongs to the palette. The illustration should not overpower the workout text; its role is to add personality to the primary plan card.

If new images are generated for this system, use the mockup as a style reference: dark red character accents, bold black outlines, warm highlights, and a grounded shadow. Avoid photorealistic stock fitness imagery unless the entire screen direction changes.

## 10. Accessibility & Contrast
Primary text should remain near-white on dark navy surfaces. Avoid placing small text directly on the bright peach portion of the background. If text must sit over a warm area, use dark text only on solid peach selected controls or add a dark surface behind it.

Red on navy is acceptable for icons and active labels, but avoid using red for long paragraphs. Peach on navy has better warmth and readability for short emphasis text. Inactive labels should remain readable but clearly secondary.

Touch targets should be large and thumb-friendly. Circular controls, date chips, and nav items should all remain at least 44 px in real device CSS pixels after scaling.

## 11. Prompting Guidance For New Screens
When asking an image or UI generator to create additional screens, describe the design as:

"A premium dark mobile fitness dashboard with a cinematic ember-red header bloom fading into deep navy, glassy rounded navy cards, red activity accents, peach selected states, bold white metric typography, pill-shaped date controls, thick rounded progress rings, and outline fitness icons. Keep the layout dense, polished, and native-app-like, with soft shadows and subtle slate borders."

Preserve these constants across new screens:
* Dark navy app base with warm red/peach top glow.
* Large rounded card containers.
* Red for active movement and alerts.
* Peach for selected or encouraging states.
* White bold numerals as the primary data language.
* Subtle slate borders and soft depth.
