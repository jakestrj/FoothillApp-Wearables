#include "pebble.h"

#define NUM_MENU_SECTIONS 2
#define NUM_FIRST_MENU_ITEMS 6
#define PADDING_X 0.0000001
#define PADDING_Y 0.0000001

static Window *s_main_window, *s_scrollLayer_window;
static SimpleMenuLayer *s_simple_menu_layer;
static ScrollLayer *s_scroll_layer;
static TextLayer *s_text_layer; 
static SimpleMenuSection s_menu_sections[NUM_MENU_SECTIONS];
static SimpleMenuItem s_first_menu_items[NUM_FIRST_MENU_ITEMS]; 
//static GFont s_ubuntu_font_24; 

//s_ubuntu_font_24 = fonts_load_custom_font(resource_get_handle(font_ubuntu_light_24); 
static char scrollLayer_regular[] = "Regular Bell Schedule\n---------------------\nP0: 6:45-7:40am\nP1: 7:45-8:41am\nP2: 8:46-9:42am\nBrk: 9:42-9:55am\nP3: 10am-11am\nP4: 11:05-12:01pm\nP5: 12:06-1:02pm\nLun: 1:02-1:32pm\nP6: 1:37-2:33pm";
static char scrollLayer_tutorial[] = "Tutorial Bell Schedule\nP0: 6:45-7:40am\nP1: 7:45-8:36am\nP2: 8:41-9:32am\nBrk: 9:32-9:44am\nP3: 9:49-10:41am\nTut: 10:46-11:15am\nP4: 11:20-12:11am\nP5: 12:16-1:07pm\nLun: 1:07-1:37pm\nP6: 1:42-2:33pm";
static char scrollLayer_late[] = "Late Start\n---------------------\nP0: 8:40-9:20am\nP1: 9:25-10:05am\nP2: 10:10-10:50am\nBrk: 10:50-11:00am\nP3: 11:05-11:48am\nP4: 11:53-12:33pm\nP5: 10:38-1:18pm\nLun: 1:18-1:48pm\nP6: 1:53-2:33pm";
static char scrollLayer_assembly[] = "Assembly Schedule\nP0: 6:45-7:40am\nP1: 7:45-8:32am\nA1: 8:37-9:27am\nA2: 9:37-10:27am\nBrk: 10:27-10:39am\nP3: 10:44-11:31am\nP4: 11:36-12:23pm\nP5: 12:28-1:14pm\nLun: 1:14-1:44am\nP6: 1:49-2:35pm";
static char scrollLayer_minimum[] = "Minimum Day\n---------------------\nP0: 7:02-7:40am\nP1: 7:45-8:22am\nP2: 8:27-9:04am\nP3: 9:09-9:46am\nBrk: 9:46-9:58am\nP4: 10:03-10:40am\nP5: 10:45-11:22am\nP6: 11:27-12:05am\n---------------------"; 
static char scrollLayer_final[] = "Final Exams\n---------------------\n----1st Exam----\n7:45-9:45am\nMinutes: 120\n---------------------\nB: 9:45-10am\n---------------------\n----2nd Exam----\n10:00-12:00am\nMinutes: 120";
static void callBack_def(char *b){
  window_stack_push(s_scrollLayer_window, false); 
  text_layer_set_font(s_text_layer,fonts_get_system_font(FONT_KEY_GOTHIC_24)); 
  text_layer_set_text(s_text_layer,b);
}
//Callbacks
static void regular_callback(int index, void *ctx) {callBack_def(scrollLayer_regular);}
static void tut_callback(int index, void *ctx) {callBack_def(scrollLayer_tutorial);}
static void late_callback(int index, void *ctx) {callBack_def(scrollLayer_late);}
static void assembly_callback(int index, void *ctx) {callBack_def(scrollLayer_assembly);}
static void min_callback(int index, void *ctx) {callBack_def(scrollLayer_minimum);}
static void final_callback(int index, void *ctx) {callBack_def(scrollLayer_final);}
//Entry point into the application 
static void main_window_load(Window *window) {  
  int num_a_items = 0;
  
//Menu Item Definitions
  s_first_menu_items[num_a_items++] = (SimpleMenuItem) {
    .title = "Regular Bell Schedule",
    .subtitle = "Mon, Wed, Fri",
    .callback = regular_callback,
  };
  s_first_menu_items[num_a_items++] = (SimpleMenuItem) {
    .title = "Tutorial Bell Schedule",
    .subtitle = "Tues, Thurs",
    .callback = tut_callback,
  };
  s_first_menu_items[num_a_items++] = (SimpleMenuItem) {
    .title = "Late Start", 
    .callback = late_callback,
  };
    s_first_menu_items[num_a_items++] = (SimpleMenuItem) {
    .title = "Assembly Schedule", 
    .callback = assembly_callback,
  };
    s_first_menu_items[num_a_items++] = (SimpleMenuItem) {
    .title = "Minimum Day", 
    .callback = min_callback,
  };
    s_first_menu_items[num_a_items++] = (SimpleMenuItem) {
    .title = "Final Exams", 
    .callback = final_callback,
  };
  
  //Section Definitions
  s_menu_sections[0] = (SimpleMenuSection) {
    .title = "2015-2016 Bell Schedule",
    .num_items = NUM_FIRST_MENU_ITEMS,
    .items = s_first_menu_items,
  };
  //Initialize main window layer
  Layer *window_layer_main = window_get_root_layer(window);
  GRect bounds = layer_get_frame(window_layer_main);
  s_simple_menu_layer = simple_menu_layer_create(bounds, window, s_menu_sections, NUM_MENU_SECTIONS, NULL);
  layer_add_child(window_layer_main, simple_menu_layer_get_layer(s_simple_menu_layer));
}
static void scrollLayer_window_load(Window *window){
  Layer *window_layer_scroll = window_get_root_layer(window);
  GRect bounds = layer_get_frame(window_layer_scroll);
  GRect max_text_bounds = GRect(PADDING_X, PADDING_Y, bounds.size.w, 2000);
  // Initialize the scroll layer
  s_scroll_layer = scroll_layer_create(bounds);
  // This binds the scroll layer to the window so that up and down map to scrolling
  // You may use scroll_layer_set_callbacks to add or override interactivity
  scroll_layer_set_click_config_onto_window(s_scroll_layer, s_scrollLayer_window);
  // Initialize the text layer and assign attributes
  s_text_layer = text_layer_create(max_text_bounds);
  text_layer_set_text(s_text_layer,scrollLayer_regular); 
  text_layer_set_font(s_text_layer, fonts_get_system_font(FONT_KEY_GOTHIC_24));
  text_layer_set_background_color(s_text_layer, GColorBlack); 
  text_layer_set_text_color(s_text_layer, GColorWhite); 
  // Trim text layer and scroll content to fit text box
  GSize max_size = text_layer_get_content_size(s_text_layer);
  text_layer_set_size(s_text_layer, max_size);
  scroll_layer_set_content_size(s_scroll_layer, GSize(bounds.size.w, max_size.h + (PADDING_X * 0.00000001)));
  // Add the layers for display
  scroll_layer_add_child(s_scroll_layer, text_layer_get_layer(s_text_layer));
  layer_add_child(window_layer_scroll, scroll_layer_get_layer(s_scroll_layer));
  
  
}
  
void main_window_unload(Window *window) {
  simple_menu_layer_destroy(s_simple_menu_layer);
}
static void scrollLayer_window_unload(Window *window) {
  text_layer_destroy(s_text_layer);
  scroll_layer_destroy(s_scroll_layer);
  //fonts_unload_custom_font(s_ubuntu_font_24);
}

static void init() {
  s_main_window = window_create();
  window_set_window_handlers(s_main_window, (WindowHandlers) {
    .load = main_window_load,
    .unload = main_window_unload,
  });

  s_scrollLayer_window = window_create();
    window_set_window_handlers(s_scrollLayer_window, (WindowHandlers){
    .load = scrollLayer_window_load,
    .unload = scrollLayer_window_unload,
  });
    window_stack_push(s_main_window, false);
}

static void deinit() {
  window_destroy(s_main_window);
  window_destroy(s_scrollLayer_window);
}

int main(void) {
  init();
  app_event_loop();
  deinit();
}
