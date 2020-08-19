Basic Features:
	search bar
	image list/grid (tasteful manner)
	single image view (Fragment)

Working process:
1. Check the API of Imgur (DONE)
    1. Do we need do login? There are two type of Authorization here, 
        1. login get client_id and client_secret
        2. Just using client_id (we use this one)
    2. What we interested is : data -> images
2. List the Hard points of the app
    1. Loading time
        1. Data Optimization
            1. Cache data to improve second time loading speed(Done)
            2. Resize Image(Done)
            3. DiffUtil
        2. Layout Optimization
            1. setHasFixSize(Done)
            2. Create globe Listener and put in Activity, pass to viewHolder. One Listener for all viewHolders(Done)
            3. setItemViewCacheSize(Done)
    2. What kind of storage, cache or photo
        1. Picasso
        2. BitMapController with LRUBItmapCache
    3. Maybe some animation. (DONE)
3. API needed: (DONE)
    1. curl --location --request GET " https://api.imgur.com/3/gallery/search/?q=cats " --header "Authorization: Client-ID {Client-ID}” 
4. Backend structure:(DONE)
    1. Mvvm
    2. Layers
        1. ViewModel
        2. Repostory
        3. DAO
        4. OkHttp
5. Frontend structure:(DONE)
    1. Mvvm
    2. Layers: 
        1. Fragment
        2. Adapter
        3. Activity
6. Tools: Viewbinding, Rxjava, Okhttp, Material Design layout(DONE)
7. Decide the UI of App: Material Design Album layout(DONE)

Strategy: Finish a workable solution first, then improve it.

Enhance Features: (If time Available)
	other search bottom(period, sort)
	Speed of response
	MutableLiveData can directly set into DataObject

Second step:
	show loading image (DONE)
	Change logo(DONE)
	Load more image (DONE)
	error handling(DONE)
	Structure indipendente(DONE)
	Back To top after reviewing the picture (BUG)
	Short reaction time
	Complete the object
	Share to outside functionality
