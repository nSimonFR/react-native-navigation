
#import <Foundation/Foundation.h>

#import <React/RCTEventEmitter.h>
#import <React/RCTBridgeModule.h>

@interface RNNEventEmitter : RCTEventEmitter <RCTBridgeModule>

-(void)sendOnAppLaunched;

-(void)sendComponentDidAppear:(NSString*)componentId componentName:(NSString*)componentName;

-(void)sendComponentDidDisappear:(NSString*)componentId componentName:(NSString*)componentName;

-(void)sendOnNavigationButtonPressed:(NSString*)componentId buttonId:(NSString*)buttonId;

-(void)sendOnNavigationEvent:(NSString *)commandName params:(NSDictionary*)params;

-(void)sendOnNavigationComment:(NSString *)commandName params:(NSDictionary*)params;

@end
