var UITree = function () {

    return {
        //main function to initiate the module
        init: function () {

            var DataSourceTree = function (options) {
                this._data  = options.data;
                this._delay = options.delay;
            };

            DataSourceTree.prototype.data = function(options, callback) {
                var self = this;
                var $data = null;

                if(!("name" in options) && !("type" in options)){
                    $data = this._data;//the root tree
                    callback({ data: $data });
                    return;
                }
                else if("type" in options && options.type == "folder") {
                    if("additionalParameters" in options && "children" in options.additionalParameters)
                        $data = options.additionalParameters.children;
                    else $data = {}//no data
                }

                if($data != null)//this setTimeout is only for mimicking some random delay
                    setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 500) + 200);

                //we have used static data here
                //but you can retrieve your data dynamically from a server using ajax call
                //checkout examples/treeview.html and examples/treeview.js for more info
            };
            
            // INITIALIZING TREE
            var tree_data = {
                'for-sale' : {name: 'For Sale', type: 'folder'}	,
                'vehicles' : {name: 'Vehicles', type: 'folder'}	,
                'rentals' : {name: 'Rentals', type: 'folder'}	,
                'real-estate' : {name: 'Real Estate', type: 'folder'}	,
                'pets' : {name: 'Pets', type: 'folder'}	,
                'tickets' : {name: 'Tickets', type: 'item'}	,
                'services' : {name: 'Services', type: 'item'}	,
                'personals' : {name: 'Personals', type: 'item'}
            }
            tree_data['for-sale']['additionalParameters'] = {
                'children' : {
                    'appliances' : {name: 'Appliances', type: 'item'},
                    'arts-crafts' : {name: 'Arts & Crafts', type: 'item'},
                    'clothing' : {name: 'Clothing', type: 'item'},
                    'computers' : {name: 'Computers', type: 'item'},
                    'jewelry' : {name: 'Jewelry', type: 'item'},
                    'office-business' : {name: 'Office & Business', type: 'item'},
                    'sports-fitness' : {name: 'Sports & Fitness', type: 'item'}
                }
            }
            tree_data['vehicles']['additionalParameters'] = {
                'children' : {
                    'cars' : {name: 'Cars', type: 'folder'},
                    'motorcycles' : {name: 'Motorcycles', type: 'item'},
                    'boats' : {name: 'Boats', type: 'item'}
                }
            }
            tree_data['vehicles']['additionalParameters']['children']['cars']['additionalParameters'] = {
                'children' : {
                    'classics' : {name: 'Classics', type: 'item'},
                    'convertibles' : {name: 'Convertibles', type: 'item'},
                    'coupes' : {name: 'Coupes', type: 'item'},
                    'hatchbacks' : {name: 'Hatchbacks', type: 'item'},
                    'hybrids' : {name: 'Hybrids', type: 'item'},
                    'suvs' : {name: 'SUVs', type: 'item'},
                    'sedans' : {name: 'Sedans', type: 'item'},
                    'trucks' : {name: 'Trucks', type: 'item'}
                }
            }

            tree_data['rentals']['additionalParameters'] = {
                'children' : {
                    'apartments-rentals' : {name: 'Apartments', type: 'item'},
                    'office-space-rentals' : {name: 'Office Space', type: 'item'},
                    'vacation-rentals' : {name: 'Vacation Rentals', type: 'item'}
                }
            }
            tree_data['real-estate']['additionalParameters'] = {
                'children' : {
                    'apartments' : {name: 'Apartments', type: 'item'},
                    'villas' : {name: 'Villas', type: 'item'},
                    'plots' : {name: 'Plots', type: 'item'}
                }
            }
            tree_data['pets']['additionalParameters'] = {
                'children' : {
                    'cats' : {name: 'Cats', type: 'item'},
                    'dogs' : {name: 'Dogs', type: 'item'},
                    'horses' : {name: 'Horses', type: 'item'},
                    'reptiles' : {name: 'Reptiles', type: 'item'}
                }
            }

            var treeDataSource = new DataSourceTree({data: tree_data});

            var treeDataSource2 = new DataSourceTree({
                data: [
                    { name: 'System Logs <div class="tree-actions"></div>', type: 'folder', additionalParameters: { id: 'F11' } },
                    { name: 'Notifications <div class="tree-actions"></div>', type: 'folder', additionalParameters: { id: 'F12' } },
                    { name: '<i class="fa fa-bell"></i> Alerts', type: 'item', additionalParameters: { id: 'I11' } },
                    { name: '<i class="fa fa-bar-chart-o"></i> Tasks', type: 'item', additionalParameters: { id: 'I12' } }
                ],
                delay: 400
            });

            var treeDataSource3 = new DataSourceTree({
                data: [
                    { name: 'Resources <div class="tree-actions"></div>', type: 'folder', additionalParameters: { id: 'F11' } },
                    { name: 'Projects <div class="tree-actions"></div>', type: 'folder', additionalParameters: { id: 'F12' } },
                    { name: 'Nike Promo 2013', type: 'item', additionalParameters: { id: 'I11' } },
                    { name: 'IPO Reports', type: 'item', additionalParameters: { id: 'I12' } }
                ],
                delay: 400
            });

            var treeDataSource4 = new DataSourceTree({
                data: [
                    { name: 'Projects<div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'folder', additionalParameters: { id: 'F11' } },
                    { name: 'Reports<div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'folder', additionalParameters: { id: 'F12' } },
                    { name: '<i class="fa fa-user"></i> Member <div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div><div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'item', additionalParameters: { id: 'I11' } },
                    { name: '<i class="fa fa-calendar"></i> Events <div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'item', additionalParameters: { id: 'I12' } },
                    { name: '<i class="fa fa-suitcase"></i> Portfolio <div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'item', additionalParameters: { id: 'I12' } }
                ],
                delay: 400
            });

            var treeDataSource5 = new DataSourceTree({
                data: [
                    { name: 'Projects<div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'folder', additionalParameters: { id: 'F11' } },
                    { name: 'Reports<div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'folder', additionalParameters: { id: 'F12' } },
                    { name: '<i class="fa fa-user"></i> Member <div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div><div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'item', additionalParameters: { id: 'I11' } },
                    { name: '<i class="fa fa-calendar"></i> Events <div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'item', additionalParameters: { id: 'I12' } },
                    { name: '<i class="fa fa-suitcase"></i> Portfolio <div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'item', additionalParameters: { id: 'I12' } }
                ],
                delay: 400
            });  

            var treeDataSource6 = new DataSourceTree({
                data: [
                    { name: 'Projects<div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'folder', additionalParameters: { id: 'F11' } },
                    { name: 'Reports<div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'folder', additionalParameters: { id: 'F12' } },
                    { name: '<i class="fa fa-user"></i> Member <div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div><div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'item', additionalParameters: { id: 'I11' } },
                    { name: '<i class="fa fa-calendar"></i> Events <div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'item', additionalParameters: { id: 'I12' } },
                    { name: '<i class="fa fa-suitcase"></i> Portfolio <div class="tree-actions"><i class="fa fa-plus"></i><i class="fa fa-trash-o"></i><i class="fa fa-refresh"></i></div>', type: 'item', additionalParameters: { id: 'I12' } }
                ],
                delay: 400
            });    

            $('#MyTree').tree({
                dataSource: treeDataSource,
                loadingHTML: '<img src="assets/img/input-spinner.gif"/>',
            });


            $('#MyTree2').tree({
                dataSource: treeDataSource2,
                loadingHTML: '<img src="assets/img/input-spinner.gif"/>',
            });

            $('#MyTree3').tree({
                dataSource: treeDataSource3,
                loadingHTML: '<img src="assets/img/input-spinner.gif"/>',
            });

            $('#MyTree4').tree({
                selectable: false,
                dataSource: treeDataSource4,
                loadingHTML: '<img src="assets/img/input-spinner.gif"/>',
            });

            $('#MyTree5').tree({
                selectable: false,
                dataSource: treeDataSource5,
                loadingHTML: '<img src="assets/img/input-spinner.gif"/>',
            });

            $('#MyTree6').tree({
                selectable: false,
                dataSource: treeDataSource6,
                loadingHTML: '<img src="assets/img/input-spinner.gif"/>',
            });
        }

    };

}();