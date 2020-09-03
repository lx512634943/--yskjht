<template>


  <div class="tu-wen">
    <Upload
      :id="'iviewUp'+identification"
      :show-upload-list="false"
      :format="['jpg','jpeg','png','gif']"
      name="richTextAccessory"
      :before-upload="beforeUpload"
      :on-success="onsuccess"
      :max-size="2048"
      multiple
      :action="baseURL"
      style="display:none;"
    >
      <Button icon="ios-cloud-upload-outline"></Button>
    </Upload>
    <quill-editor
      v-model="content"
      ref="myQuillEditor"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @change="onEditorChange($event)"
    >
    </quill-editor>
  </div>

</template>

<script>
  import {quillEditor} from 'vue-quill-editor'
  // 工具栏配置
  const toolbarOptions = [
    ['bold', 'italic', 'underline'],
    [{'size': ['small', false, 'large', 'huge']}],
    [{'color': []}, {'background': []}],          // dropdown with defaults from theme
    [{'font': []}],
    [{'align': []}],
    ['image'],                                // remove formatting button
  ];


  export default {
    name: "richText",
    props: {
      value: {
        type: String,
        default: ''
      },
      isExtra: {
        type: Boolean,
        default: false
      }
    },
    mounted() {
      var data = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
      var nums = "";
      for (var i = 0; i < 6; i++) {
        var r = parseInt(Math.random() * 61);
        nums += data[r];
      }
      this.content = this.value;
      this.identification = nums;

    },
    data() {
      return {
        uploadList: [], //富文本编辑器的图文列表
        content: '', //富文本里的内容
        editorOption: {//富文本编辑器的工具栏
          modules: {
            toolbar: {
              container: this.option(),  // 工具栏
              handlers: {
                'image': this.imgClick
              }
            },
          },
          imageResize: {}, //自定义拉伸
          placeholder: '请输入文章内容',
        },
        contentTxt: '',//富文本编辑器里的前面100个文字
        identification: '',
      }
    },
    computed:{
      baseURL () {
        return this.$config.urlPath + 'admin/news/upload'
      }
    },
    methods: {

      option() {
        if (this.isExtra) {
          return [
            ['bold', 'italic', 'underline'],
          ];
        } else {
          return toolbarOptions;
        }

      },
      imgClick(value) {
        console.log(value)
        if (value) {
          let id = '#iviewUp' + this.identification + ' input';
          document.querySelector(id).click()
        } else {
          this.quill.format('image', false);
        }
      },
      onEditorBlur(e) {
        // console.log('blur',e)
      },
      onEditorFocus(e) {
      },
      onEditorChange(e) {
      },
      // 富文本编辑器的上传图片成功的操作
      beforeUpload(file) {
        this.$Spin.show();
        if (file.size > 1000 * 1024 * 2) {
          this.$Spin.hide();
          this.$Notice.warning({
            title: '上传错误',
            desc: '文件大小请勿超过2M'
          });
          return false;
        }
        console.log(file)

        this.$Spin.hide();
        return true;
      },
      onsuccess(file){
        let quill = this.$refs.myQuillEditor.quill;
        // 获取光标所在位置
        let length = quill.getSelection().index;
        // 插入图片，res为服务器返回的图片链接地址
        console.log("http://localhost:81/"+(file.filePath));
        quill.insertEmbed(length, 'image', 'http://localhost:8080/SoJpt/'+(file.filePath) );
        // quill.insertEmbed(length, 'image', 'http://39.100.241.51:81/'+(file.filePath) );
        // 调整光标到最后
        quill.setSelection(length + 1)
      }
    },
    watch: {
      content(val, oldval) {
        this.$emit('on-change', val)
      },
      value(val, oldval) {
        this.content = val;
      }
    }
  }
</script>

<style scoped>
  .edit_container {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }

</style>
