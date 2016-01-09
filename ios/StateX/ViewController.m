//
//  ViewController.m
//  StateXDemo
//
//  Created by James Deng on 29/12/2015.
//

#import <Foundation/Foundation.h>

#import "ViewController.h"
#import "RCTStatex.h"

@implementation ViewController

// MARK: Properties
int count = 0;

// MARK: Actions

- (IBAction)increase {
    count++;
    [self sync];
}

- (IBAction)decrease {
    count--;
    [self sync];
}

- (void)sync {
    [self.counterLabel setText:[NSString stringWithFormat:@"%d", count]];
}
@end