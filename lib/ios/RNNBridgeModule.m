#import "RNNBridgeModule.h"

@implementation RNNBridgeModule {
	RNNCommandsHandler* _commandsHandler;
}
@synthesize bridge = _bridge;
RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue {
	return dispatch_get_main_queue();
}

-(instancetype)initWithCommandsHandler:(RNNCommandsHandler *)commandsHandler {
	self = [super init];
	_commandsHandler = commandsHandler;
	return self;
}

#pragma mark - JS interface

RCT_EXPORT_METHOD(setRoot:(NSDictionary*)layout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler setRoot:layout completion:^{
		resolve(layout);
	}];
}

RCT_EXPORT_METHOD(mergeOptions:(NSString*)componentId options:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler mergeOptions:componentId options:options completion:^{
		resolve(componentId);
	}];
}

RCT_EXPORT_METHOD(setDefaultOptions:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler setDefaultOptions:options completion:^{
		resolve(nil);
	}];
}

RCT_EXPORT_METHOD(push:(NSString*)componentId layout:(NSDictionary*)layout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler push:componentId layout:layout completion:^{
		resolve(componentId);
	} rejection:reject];
}

RCT_EXPORT_METHOD(pop:(NSString*)componentId options:(NSDictionary*)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler pop:componentId options:(NSDictionary*)options completion:^{
		resolve(componentId);
	} rejection:reject];
}

RCT_EXPORT_METHOD(setStackRoot:(NSString*)componentId layout:(NSDictionary*)layout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler setStackRoot:componentId layout:layout completion:^{
		resolve(componentId);
	} rejection:reject];
}

RCT_EXPORT_METHOD(popTo:(NSString*)componentId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler popTo:componentId completion:^{
		resolve(componentId);
	} rejection:reject];
}

RCT_EXPORT_METHOD(popToRoot:(NSString*)componentId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler popToRoot:componentId completion:^{
		resolve(componentId);
	} rejection:reject];
}

RCT_EXPORT_METHOD(showModal:(NSDictionary*)layout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler showModal:layout completion:^{
		resolve(nil);
	}];
}

RCT_EXPORT_METHOD(dismissModal:(NSString*)componentId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler dismissModal:componentId completion:^{
		resolve(componentId);
	}];
}

RCT_EXPORT_METHOD(dismissAllModals:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler dismissAllModalsWithCompletion:^{
		resolve(nil);
	}];
}

RCT_EXPORT_METHOD(showOverlay:(NSDictionary*)layout resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler showOverlay:layout completion:^{
		resolve(layout[@"id"]);
	}];
}

RCT_EXPORT_METHOD(dismissOverlay:(NSString*)componentId resolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
	[_commandsHandler dismissOverlay:componentId completion:^{
		resolve(@(1));
	}];
}

@end

