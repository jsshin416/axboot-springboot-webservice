var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: 'GET',
            //url: '/api/v1/eduwebtest',
            url: '/api/v1/eduwebtest/queryDsl',
            data: caller.searchView.getData(),
            callback: function (res) {
                caller.gridView01.setData(res);
            },
            options: {
                // axboot.ajax 함수에 2번째 인자는 필수가 아닙니다. ajax의 옵션을 전달하고자 할때 사용합니다.
                onError: function (err) {
                    console.log(err);
                },
            },
        });

        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveList = [].concat(caller.gridView01.getData(''));
        saveList = saveList.concat(caller.gridView01.getData('deleted'));

        axboot.ajax({
            type: 'PUT',
            // url: '/api/v1/eduwebtest',
            url: '/api/v1/eduwebtest/queryDsl',
            data: JSON.stringify(saveList),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push('저장 되었습니다');
            },
        });
    },
    ITEM_CLICK: function (caller, act, data) {},
    ITEM_ADD: function (caller, act, data) {
        caller.gridView01.addRow();
    },
    ITEM_DEL: function (caller, act, data) {
        caller.gridView01.delRow('selected');
    },
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != 'error') {
            return result;
        } else {
            // 직접코딩
            return false;
        }
    },
    EXCEL_DOWN: function (caller, act, data) {
        let frm = document['excelForm'];
        frm.action = '/api/v1/edu/teachGridForm/excelDown';
        frm.submit();
    },
    EXCEL_UPROAD: function (caller, act, data) {
        axboot.ajax({
            type: 'GET',
            url: '/api/v1/edu/teachGridForm/exelUpload',
            data: data,
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
        });
    },
});

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    this.pageButtonView.initView();
    this.searchView.initView();
    this.gridView01.initView();

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, 'data-page-btn', {
            search: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            save: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            excel: function () {
                ACTIONS.dispatch(ACTIONS.EXCEL_DOWN);
            },
            fn1: function () {
                ACTIONS.dispatch(ACTIONS.EXCEL_UPROAD);
            },
        });
    },
});

//== view 시작
/**
 * searchView
 */
fnObj.searchView = axboot.viewExtend(axboot.searchView, {
    initView: function () {
        this.target = $(document['searchView0']);
        this.target.attr('onsubmit', 'return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);');
        this.company = $('#company');
        this.ceo = $('#ceo');
        this.bizno = $('#bizno');
        this.useYn = $('.js-useYn');
        //this.useYn = $('#useYn');
    },
    getData: function () {
        return {
            pageNumber: this.pageNumber || 0,
            pageSize: this.pageSize || 2,
            company: this.company.val(),
            ceo: this.ceo.val(),
            bizno: this.bizno.val(),
            useYn: this.useYn.val(),
        };
    },
});
// fnObj.selectItems = [
//     { value: '', text: '전체' },
//     { value: 'Y', text: '사용' },
//     { value: 'N', text: '미사용' },
// ];
/**
 * gridView
 */
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: true,
            frozenColumnIndex: 0,
            multipleSelect: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                { key: 'companyNm', label: COL('company.name'), width: 160, align: 'left', editor: 'text' },
                { key: 'ceo', label: COL('company.coe'), width: 350, align: 'left', editor: 'text' },
                { key: 'bizno', label: COL('company.bizno'), width: 100, align: 'center', editor: 'text' },
                { key: 'tel', label: COL('company.tel'), width: 100, align: 'center', editor: 'text' },
                { key: 'email', label: COL('company.email'), width: 100, align: 'center', editor: 'text' },
                {
                    key: 'useYn',
                    label: '사용여부',
                    width: 100,
                    align: 'center',
                    editor: 'text',
                },
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, { selectedClear: true });
                },
            },
        });

        axboot.buttonClick(this, 'data-grid-view-01-btn', {
            add: function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            delete: function () {
                ACTIONS.dispatch(ACTIONS.ITEM_DEL);
            },
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == 'modified' || _type == 'deleted') {
            list = ax5.util.filter(_list, function () {
                return this.id;
            });
        } else {
            list = _list;
        }
        return list;
    },
    addRow: function () {
        this.target.addRow({ __created__: true }, 'last');
    },
});
