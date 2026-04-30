# Design System: Bloom Mobile Dashboard
**Project ID:** Not provided; derived from local screenshot assets `ui/plum-dashboard-light.png` and `ui/plum-dashboard-dark.png`

## 1. Visual Theme & Atmosphere
Bloom is a warm, polished mobile wellness dashboard with an optimistic fitness-app personality. The interface feels energetic and friendly through coral-red activity accents, soft petal-like logo geometry, celebratory illustrations, and rounded iOS-style controls. The dashboard is information-dense but not cramped: key health metrics sit in large paired summary cards, secondary habit and workout prompts span the full width, and the bottom navigation remains persistent and highly tactile.

The light mode is airy, creamy, and softly luminous. It uses warm white surfaces, pale peach shadows, and red-orange accents to make the experience feel clean, healthy, and approachable.

The dark mode is cinematic, ember-lit, and high-contrast. It uses near-black plum surfaces with smoky red glows, rose-tinted borders, and bright coral highlights so the same dashboard feels focused and premium without losing warmth.

## 2. Color Palette & Roles

### Shared Brand Colors
* **Bloom Coral Flame (#FD6055):** Primary action color for the floating add button, progress fills, active navigation state, activity icons, and key numeric emphasis.
* **Fresh Coral Highlight (#FE7867):** Softer active accent for chart bars, ring gradients, badges, and illustrated objects.
* **Pressed Ember Red (#B61F0C):** Strong brand red used in light-mode wordmark treatments and high-emphasis labels.
* **Soft Petal Coral (#F9A495):** Gentle tint used inside activity rings, confetti, illustration highlights, and ambient glows.
* **Recovery Green (#2EBF5A):** Positive health state for recovery score icons, progress bars, and "Good" status text.
* **Fresh Leaf Green (#57CB77):** Brighter success highlight for active recovery indicators.
* **Food Blue (#4287FB):** Secondary category color for food-related icons in light mode.
* **Streak Gold (#FDA92F):** Warm achievement color for trophies, streak icons, and motivational reward states.

### Light Mode
* **Warm Milk Canvas (#FEF8F5):** App background; gives the screen a soft, healthy warmth instead of stark white.
* **Pearl Card Surface (#FEF9F7):** Main card and panel fill; keeps content blocks readable while staying close to the canvas.
* **Bright Porcelain Surface (#FEFBF9):** Slightly lifted areas such as navigation, card interiors, and circular icon backgrounds.
* **Peach Hairline Border (#F1DAD5):** Subtle outline for cards, pills, circular buttons, and bottom navigation.
* **Primary Ink (#0E0B0B):** Main text color for headings, labels, and key body copy.
* **Soft Charcoal (#353238):** Secondary text for explanations, dates, legends, and supporting labels.
* **Muted Warm Gray (#8C868C):** Tertiary text, inactive chart context, and subdued metadata.
* **Pale Activity Track (#FEEEEA):** Inactive portions of rings, calendar heatmaps, and chart tracks.

### Dark Mode
* **Midnight Plum Canvas (#08070C):** Main app background; nearly black with a subtle plum warmth.
* **Deep Wine Glow (#10020C):** Ambient top-area glow behind the brand, header, and hero content.
* **Smoked Card Surface (#12090F):** Primary card fill; dark but visibly lifted from the page.
* **Bottom Bar Charcoal (#0C0B0F):** Navigation surface and persistent control background.
* **Rosewood Border (#5D292B):** Card, pill, and navigation outlines; adds warmth without becoming bright.
* **Burnt Plum Shadow (#320418):** Deep accent shadow used behind active states and dark-mode gradients.
* **Primary White (#FFFFFF):** Main text color for headings and important values.
* **Soft Ash Text (#DFD9DB):** Secondary body copy and supporting labels.
* **Muted Rose Gray (#AFABAC):** Tertiary labels, inactive values, and explanatory text.
* **Dark Activity Track (#1E1A1D):** Inactive portions of dark rings, heatmaps, dividers, and control wells.

## 3. Typography Rules
Use a modern system sans serif with an iOS feel, matching SF Pro or an equivalent geometric humanist sans. Typography should be clear, round, and friendly, with neutral letter spacing.

Headlines are bold and confident. The brand wordmark uses a large, heavy title weight with warm coral coloring and a subtle dimensional glow. Screen greetings and card titles use bold weights to create quick scanning anchors. Primary metric numbers are extra-large and heavy, while units, explanations, legends, and dates use regular or medium weights with softer contrast.

Maintain a strong hierarchy: brand title at the top, greeting below it, paired card titles next, large numeric metrics within each card, then short descriptive copy. Avoid all-caps except for compact labels such as "KCAL," "BETA," and small chart legends.

## 4. Component Stylings
* **Buttons:** Primary creation is a large circular coral button with a white plus icon, gentle gloss, and a soft shadow or glow. Secondary actions are pill-shaped outlines with coral text, used for "Edit," "Start tracking," and "Quick check in." Icon-only utility buttons use circular wells with thin borders and centered line icons.
* **Cards/Containers:** Cards use generously rounded corners with a thin warm border. Light cards sit on pearl surfaces with barely visible peach outlines and soft ambient shadows. Dark cards sit on smoked plum surfaces with rosewood outlines and low, diffused glow. Cards should feel like soft glass panels, not heavy blocks.
* **Inputs/Forms:** Use the same pill-shaped geometry as secondary actions. In light mode, fields should use bright porcelain fills with peach hairline borders. In dark mode, fields should use smoked card fills with rosewood borders. Focus states should glow with Bloom Coral Flame (#FD6055), while disabled states recede into muted gray tracks.
* **Charts & Rings:** Activity rings use thick rounded strokes with coral gradients and pale or dark inactive tracks. Heatmaps use small rounded squares with stepped coral intensity from pale petal tones to saturated Bloom Coral Flame (#FD6055). Tiny bar charts use dot-like rounded marks with occasional taller bars for peaks.
* **Badges:** "BETA" badges are compact pills with coral text and a translucent coral-pink fill. Keep badges small and aligned to headings rather than making them primary actions.
* **Navigation:** The bottom navigation is a floating rounded bar. Active navigation receives a soft rounded highlight behind the icon and label, with coral iconography and text. Inactive items use high-contrast line icons and restrained labels. The floating plus action overlaps visually with the navigation rhythm while staying distinctly primary.
* **Illustrations & Icons:** Icons are rounded line icons with friendly stroke endings. Illustrations are glossy coral 3D objects or confetti-like decorative pieces, always supporting the fitness and habit-tracking context.

## 5. Layout Principles
Design for a tall mobile viewport with iOS safe-area spacing. The header uses a clear brand block on the left and notification/action controls on the right. The top metric area uses a two-column grid for primary summaries, followed by full-width promotional cards, a wider consistency panel, and smaller two-column stat cards near the bottom.

Spacing is compact but breathable. Use consistent outer margins, even gutters between cards, and clear vertical stacking. Cards should align to a shared grid, with text anchored left and illustrations or circular charts balanced to the right or center. The interface should always prioritize fast scanning: large numbers first, status labels second, explanatory copy third.

Both light and dark modes must preserve the same component geometry, hierarchy, and spacing. Only the surface colors, text contrast, border warmth, and glow intensity should change between themes.
