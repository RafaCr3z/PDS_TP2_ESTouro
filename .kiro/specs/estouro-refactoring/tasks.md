# Implementation Plan

- [x] 1. Implement Strategy Pattern for Attack Modes

  - Replace switch statements in torre classes with Strategy pattern implementation
  - Create new attack strategy classes (EstrategiaLonge, EstrategiaForte)
  - Update existing strategy classes to ensure consistent interface implementation
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 6.1, 6.2_

- [x] 1.1 Create enhanced attack strategy interface and base implementations


  - Enhance EstrategiaAtaque interface with proper method signatures
  - Implement EstrategiaLonge class for targeting farthest bloons
  - Implement EstrategiaForte class for targeting highest value bloons
  - _Requirements: 1.1, 6.1, 6.2_

- [x] 1.2 Update ConfiguradorTorres to support new strategies


  - Modify ConfiguradorTorres class to include new attack strategies
  - Ensure proper strategy registration and selection mechanisms
  - _Requirements: 6.4, 6.5_

- [x] 1.3 Write unit tests for attack strategies






  - Create unit tests for EstrategiaLonge target selection logic
  - Create unit tests for EstrategiaForte value-based targeting
  - Test strategy interface compliance for all implementations
  - _Requirements: 1.1, 6.1, 6.2_

- [x] 2. Refactor Torre Classes Using Template Method Pattern


  - Extract common attack logic into TorreDefault abstract class
  - Implement template method pattern to eliminate code duplication
  - Refactor existing torre subclasses to use template method structure
  - _Requirements: 2.1, 2.2, 2.3, 2.4_

- [x] 2.1 Create template method in TorreDefault class


  - Implement atacar() template method with common attack algorithm
  - Define abstract methods for subclass customization (criarProjeteis, orientarTorre, filtrarAlvosNoAlcance)
  - Extract common helper methods for target filtering and distance calculations
  - _Requirements: 2.1, 2.2, 2.3_

- [x] 2.2 Refactor existing torre classes to use template method


  - Update TorreMacaco to implement abstract methods from template
  - Update TorreOctogonal to use template method structure
  - Update TorreCanhao to implement template method pattern
  - Update TorreMorteiro to use template method approach
  - Update TorreBalista to implement template method structure
  - Update TorreNinja to use template method pattern
  - _Requirements: 2.1, 2.2, 2.4_

- [ ] 2.3 Write unit tests for template method implementation

  - Test template method execution flow in TorreDefault
  - Test abstract method implementations in torre subclasses
  - Verify code duplication reduction through shared logic
  - _Requirements: 2.1, 2.2, 2.5_

- [ ] 3. Eliminate Instanceof Usage with Polymorphism
  - Replace all instanceof checks with polymorphic method calls
  - Implement visitor pattern where type-specific behavior is needed
  - Use method overloading for handling different object types
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ] 3.1 Identify and replace instanceof usage in projectile handling
  - Locate instanceof usage in projectile-bloon interaction code
  - Replace with polymorphic method calls using virtual dispatch
  - Implement proper method overloading for different projectile types
  - _Requirements: 3.1, 3.2, 3.3_

- [ ] 3.2 Implement visitor pattern for bloon-specific operations
  - Create BloonVisitor interface for type-specific operations
  - Implement concrete visitor classes for different bloon operations
  - Update bloon classes to accept visitors properly
  - _Requirements: 3.2, 3.3, 3.4_

- [ ]* 3.3 Write unit tests for polymorphic behavior
  - Test visitor pattern implementation with different bloon types
  - Verify instanceof elimination in all identified locations
  - Test method overloading for type-specific behavior
  - _Requirements: 3.1, 3.2, 3.5_

- [ ] 4. Fix BloonFabricante Cloning Issues
  - Implement proper prototype pattern with deep cloning
  - Fix shared state issues causing erratic bloon behavior
  - Ensure created bloons are independent instances
  - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5_

- [ ] 4.1 Implement proper prototype pattern in BloonFabricante
  - Modify BloonFabricante to store prototypes instead of shared instances
  - Implement deep cloning for prototype bloons
  - Fix clone() method to create independent BloonFabricante instances
  - _Requirements: 4.1, 4.4, 4.5_

- [ ] 4.2 Fix bloon creation and independence issues
  - Ensure created bloons have independent state and behavior
  - Fix speed and direction anomalies in fabricated bloons
  - Prevent backward movement and erratic behavior patterns
  - _Requirements: 4.2, 4.3_

- [ ]* 4.3 Write unit tests for BloonFabricante fixes
  - Test proper cloning behavior and instance independence
  - Test created bloon behavior and movement patterns
  - Verify elimination of shared state issues
  - _Requirements: 4.1, 4.2, 4.3, 4.5_

- [ ] 5. Implement Torre Sniper with Infinite Range
  - Create new TorreSniper class with infinite range capability
  - Implement instant-hit projectile with damage transition behavior
  - Add player-controlled direction setting functionality
  - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5_

- [ ] 5.1 Create TorreSniper class implementation
  - Implement TorreSniper extending TorreDefault template method
  - Create infinite range targeting with line-of-sight filtering
  - Implement player-controlled direction setting mechanism
  - _Requirements: 5.1, 5.4, 5.5_

- [ ] 5.2 Implement sniper projectile behavior
  - Create ProjetilSniper class with instant-hit capability
  - Implement damage 5 to first target with immediate effect
  - Add behavior transition to normal dart after first impact
  - _Requirements: 5.2, 5.3_

- [ ] 5.3 Update TorreCreator for sniper tower
  - Add sniper tower creation method to TorreCreator class
  - Update factory registration to include sniper tower type
  - Ensure proper image loading and configuration
  - _Requirements: 8.1, 8.2_

- [ ]* 5.4 Write unit tests for Torre Sniper
  - Test infinite range targeting and line-of-sight filtering
  - Test instant-hit projectile behavior and damage application
  - Test direction setting and player control functionality
  - _Requirements: 5.1, 5.2, 5.3, 5.5_

- [ ] 6. Implement Temporary Protection Bloon Decorators
  - Create BloonArmaduraTemporaria decorator for piercing immunity
  - Create BloonEscudoTemporario decorator for explosion immunity
  - Update Zeppelin bloons to create offspring with temporary protections
  - _Requirements: 7.1, 7.2, 7.3, 7.4, 7.5_

- [ ] 6.1 Create temporary armor decorator implementation
  - Implement BloonArmaduraTemporaria extending BloonDecorator
  - Add impact counter for piercing projectile immunity
  - Implement protection expiration after specified impacts
  - _Requirements: 7.1, 7.4_

- [ ] 6.2 Create temporary shield decorator implementation
  - Implement BloonEscudoTemporario extending BloonDecorator
  - Add explosion counter for explosive projectile immunity
  - Implement protection expiration after specified explosions
  - _Requirements: 7.2, 7.4_

- [ ] 6.3 Update Zeppelin bloons for temporary protection offspring
  - Modify ZeppelinMetal to create bloons with 8-impact armor
  - Modify ZeppelinPreto to create bloons with 12-explosion shield
  - Ensure proper decorator application to created bloons
  - _Requirements: 7.5_

- [ ]* 6.4 Write unit tests for temporary protection decorators
  - Test armor decorator impact counting and immunity behavior
  - Test shield decorator explosion counting and immunity behavior
  - Test protection expiration and vulnerability restoration
  - _Requirements: 7.1, 7.2, 7.3, 7.4_

- [ ] 7. Update Factory Classes and Configuration
  - Update BloonCreator to support new bloon decorator types
  - Ensure TorreCreator supports all tower types including sniper
  - Update configuration files with new tower and strategy information
  - _Requirements: 8.1, 8.2, 8.3, 8.4_

- [ ] 7.1 Update BloonCreator for new decorator types
  - Add factory methods for temporary protection decorators
  - Update Zeppelin creation methods to apply decorators to offspring
  - Ensure proper registration of all bloon types in factory
  - _Requirements: 8.1, 8.2_

- [ ] 7.2 Update configuration files
  - Update TowerInfo.txt with sniper tower information
  - Add new attack strategy configurations
  - Ensure proper configuration file parsing for new elements
  - _Requirements: 8.1, 8.4_

- [ ]* 7.3 Write integration tests for factory updates
  - Test BloonCreator with new decorator types
  - Test TorreCreator with sniper tower creation
  - Test configuration file loading and parsing
  - _Requirements: 8.1, 8.2, 8.4_

- [ ] 8. Integration Testing and System Verification
  - Perform comprehensive integration testing of all refactored components
  - Verify all existing functionality is preserved after refactoring
  - Test new features integration with existing game mechanics
  - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5_

- [ ] 8.1 Test existing functionality preservation
  - Verify all existing tower behaviors and characteristics are maintained
  - Test bloon movement, interaction, and game mechanics preservation
  - Ensure projectile types and damage calculations remain correct
  - _Requirements: 9.1, 9.2, 9.3_

- [ ] 8.2 Test new features integration
  - Test sniper tower integration with existing game systems
  - Test new attack strategies with all compatible tower types
  - Test temporary protection decorators with existing projectile systems
  - _Requirements: 9.4, 9.5_

- [ ]* 8.3 Perform comprehensive system testing
  - Execute full game scenarios with all refactored components
  - Test performance and memory usage of refactored system
  - Verify user interface compatibility with refactored backend
  - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5_