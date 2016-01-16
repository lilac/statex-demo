//
//  ViewController.m
//  StateXDemo
//
//  Created by James Deng on 29/12/2015.
//

#import <Foundation/Foundation.h>

#import "ViewController.h"
#import "RCTStatex.h"
#import "AppDelegate.h"
#import "RCTEventDispatcher.h"
#import "RCTBridge.h"

static NSString *COUNT_KEY = @"count";

@implementation ViewController {
  StateX *statex;
  AppDelegate __weak *appDelegate;
}

- (id)initWithCoder:(NSCoder *)aDecoder
{
  self = [super initWithCoder:aDecoder];
  if (self) {
    appDelegate = (AppDelegate *)[UIApplication sharedApplication].delegate;

    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(sync) name:COUNT_KEY object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(stateXDidLoad) name:RCTJavaScriptDidLoadNotification object:nil];
  }
  return self;
}

// MARK: Actions

- (IBAction)increase {
  [appDelegate.bridge.eventDispatcher sendDeviceEventWithName:@"add" body:nil];
}

- (IBAction)decrease {
  [appDelegate.bridge.eventDispatcher sendDeviceEventWithName:@"decrease" body:nil];
}

- (void)stateXDidLoad {
  statex = [appDelegate.bridge moduleForClass:[StateX class]];
  [self sync];
}

- (void)sync {
  NSDictionary *errors;
  NSString *value = [statex get:COUNT_KEY errorOut:&errors];
  if (value == NULL) {
    value = @"0";
  }
  [self.counterLabel setText:value];
}
@end