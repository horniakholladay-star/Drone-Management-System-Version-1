/**
 * 无人机管理系统 — 自定义脚本
 */

$(function () {

    // 自动关闭提示消息
    $('.alert-dismissible').delay(3000).fadeOut(500, function () {
        $(this).alert('close');
    });

    // 表单校验增强 — 在提交前进行检查
    $('.drone-form').on('submit', function (e) {
        var form = $(this);
        var isValid = true;

        // 检查必填字段
        form.find('[required]').each(function () {
            var $field = $(this);
            var value = $field.val();
            if (!value || value.trim() === '') {
                isValid = false;
                $field.closest('.form-group').addClass('has-error');
            } else {
                $field.closest('.form-group').removeClass('has-error');
            }
        });

        // 检查数字范围
        form.find('input[type="number"]').each(function () {
            var $field = $(this);
            var value = parseFloat($field.val());
            var min = parseFloat($field.attr('min'));
            var max = parseFloat($field.attr('max'));

            if (!isNaN(value)) {
                if (!isNaN(min) && value < min) {
                    isValid = false;
                    $field.closest('.form-group').addClass('has-error');
                }
                if (!isNaN(max) && value > max) {
                    isValid = false;
                    $field.closest('.form-group').addClass('has-error');
                }
            }
        });

        if (!isValid) {
            e.preventDefault();
            // 滚动到第一个错误字段
            var firstError = form.find('.has-error').first();
            if (firstError.length) {
                $('html, body').animate({
                    scrollTop: firstError.offset().top - 100
                }, 300);
            }
        }
    });

    // 删除确认 — 使用 AJAX 提交删除表单
    $('#deleteForm').on('submit', function (e) {
        e.preventDefault();
        var form = $(this);
        var id = form.find('input[name="id"]').val();

        $.ajax({
            url: form.attr('action'),
            type: 'POST',
            data: { id: id },
            success: function (result) {
                if (result.code === 0) {
                    // 成功 → 关闭弹窗，刷新页面
                    $('#deleteModal').modal('hide');
                    location.reload();
                } else {
                    alert('删除失败: ' + result.message);
                }
            },
            error: function () {
                alert('删除请求失败，请稍后重试');
            }
        });
    });

    // 输入框实时去除 has-error
    $('.drone-form input, .drone-form select, .drone-form textarea')
        .on('change keyup', function () {
            $(this).closest('.form-group').removeClass('has-error');
        });

});
